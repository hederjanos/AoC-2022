package day._16;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ValveSystem {

    private final Valve start;
    private final Map<Integer, Valve> valves;
    private final Map<Integer, Set<Integer>> connections;

    public ValveSystem(List<String> puzzle) {
        valves = initValves(puzzle);
        connections = initConnections(puzzle);
        start = valves.get("AA".hashCode());
    }

    private Map<Integer, Valve> initValves(List<String> puzzle) {
        return puzzle.stream()
                .map(line -> {
                    String[] reportLineParts = line.split("; ");
                    String label = extractLabels(reportLineParts[0], 2).get(0);
                    int flowRate = Integer.parseInt(reportLineParts[0].split("=")[1]);
                    return new Valve(label, flowRate);
                })
                .collect(Collectors.toMap(Valve::hashCode, valve -> valve));
    }

    private List<String> extractLabels(String reportLinePart, int numberOfChars) {
        List<String> labels = new ArrayList<>();
        Pattern pattern = Pattern.compile("[A-Z]{" + numberOfChars + "}");
        Matcher matcher = pattern.matcher(reportLinePart);
        while (matcher.find()) {
            labels.add(matcher.group());
        }
        return labels;
    }

    private Map<Integer, Set<Integer>> initConnections(List<String> puzzle) {
        return puzzle.stream()
                .map(line -> extractLabels(line, 2))
                .collect(Collectors.toMap(
                        labels -> labels.get(0).hashCode(),
                        labels -> labels.subList(1, labels.size()).stream()
                                .map(String::hashCode)
                                .collect(Collectors.toSet()))
                );
    }

    public ValveSystemState findMostReleasedPressureState() {
        Map<ValvePair, Integer> shortestPaths = findShortestPaths();
        Deque<ValveSystemState> valveSystemStates = new ArrayDeque<>();
        ValveSystemState initState = new ValveSystemState(start, 0, 0, 0, new HashSet<>());
        valveSystemStates.offer(initState);
        while (!valveSystemStates.isEmpty()) {
            ValveSystemState currentState = valveSystemStates.pop();
            for (Valve valve : findValvesWithFlowRates()) {
                if (!currentState.getValve().equals(valve)) {
                    // equivalent with minutes to reach here
                    int numberOfStepsFromCurrent = shortestPaths.get(new ValvePair(currentState.getValve(), valve));
                    if (!currentState.getOpenValves().contains(valve) && currentState.getElapsedTimeInMinute() + numberOfStepsFromCurrent < 30) {
                        valveSystemStates.offer(createState(currentState, valve, numberOfStepsFromCurrent));
                    }
                }
            }
            if (getPossibleReleasedPressure(currentState) > getPossibleReleasedPressure(initState)) {
                initState = currentState;
            }
        }
        return initState;
    }

    public int getPossibleReleasedPressure(ValveSystemState currentState) {
        int remainingTimeInMinute = 30 - currentState.getElapsedTimeInMinute();
        int currentReleasedPressure = currentState.getReleasedPressure();
        while (remainingTimeInMinute > 0) {
            currentReleasedPressure += currentState.getPressurePerMinute();
            remainingTimeInMinute--;
        }
        return currentReleasedPressure;
    }

    private ValveSystemState createState(ValveSystemState current, Valve valve, int numberOfStepsFromCurrent) {
        int elapsedTimeInMinute = current.getElapsedTimeInMinute() + numberOfStepsFromCurrent + 1;
        int pressurePerMinute = current.getPressurePerMinute() + valve.getFlowRate();
        int releasedPressure = current.getReleasedPressure() + (numberOfStepsFromCurrent + 1) * current.getPressurePerMinute();
        Set<Valve> openValves = new HashSet<>(current.getOpenValves());
        openValves.add(valve);
        return new ValveSystemState(valve, elapsedTimeInMinute, pressurePerMinute, releasedPressure, openValves);
    }

    private Map<ValvePair, Integer> findShortestPaths() {
        Set<Valve> valvesWithFlowRates = findValvesWithFlowRates();
        valvesWithFlowRates.add(start);
        Map<ValvePair, Integer> shortestPaths = new HashMap<>();
        valvesWithFlowRates
                .forEach(from -> valvesWithFlowRates.stream()
                        .filter(to -> !from.equals(to) && !to.equals(start))
                        .forEach(to -> {
                            ValvePair valvePair = new ValvePair(from, to);
                            shortestPaths.put(valvePair, findShortestPath(valvePair));
                        }));
        return shortestPaths;
    }

    private Set<Valve> findValvesWithFlowRates() {
        return valves.values().stream()
                .filter(valve -> valve.getFlowRate() != 0)
                .collect(Collectors.toSet());
    }

    private int findShortestPath(ValvePair valvePair) {
        Set<Valve> visitedValves = new HashSet<>();
        int shortestPathLength = 0;
        Deque<ValveState> valveStates = new ArrayDeque<>();
        valveStates.offer(new ValveState(valvePair.getFrom(), 0));
        visitedValves.add(valvePair.getFrom());
        while (!valveStates.isEmpty()) {
            ValveState current = valveStates.pop();
            if (current.getValve().equals(valvePair.getTo())) {
                shortestPathLength = current.getNumberOfSteps();
                break;
            }
            for (Valve valve : getNeighboursOfValve(current.getValve().hashCode())) {
                if (!visitedValves.contains(valve)) {
                    valveStates.offer(new ValveState(valve, current.getNumberOfSteps() + 1));
                    visitedValves.add(valve);
                }
            }
        }
        return shortestPathLength;
    }

    public List<Valve> getNeighboursOfValve(int hashCode) {
        Valve valve = valves.get(hashCode);
        if (valve == null) {
            throw new IllegalArgumentException();
        } else {
            return connections.get(hashCode).stream().map(valves::get).collect(Collectors.toList());
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        connections.forEach((key, value) -> {
            appendValveByKey(valves.get(key), stringBuilder, ":");
            value.forEach(integer -> {
                appendValveByKey(valves.get(integer), stringBuilder, ",");
                stringBuilder.append(" ");
            });
            stringBuilder.replace(stringBuilder.length() - 3, stringBuilder.length(), "");
            stringBuilder.append("\n");
        });
        return stringBuilder.toString().trim();
    }

    private void appendValveByKey(Valve valve, StringBuilder stringBuilder, String separator) {
        stringBuilder.append(valve.getLabel())
                .append("[")
                .append(valve.getFlowRate())
                .append("]").append(separator).append(" ");
    }

}
