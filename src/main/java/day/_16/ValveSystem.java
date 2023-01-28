package day._16;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ValveSystem {

    private final Valve start;
    private final Map<Integer, Valve> valves;
    private final Map<Integer, Set<Integer>> connections;
    private final Set<Valve> valvesWithFlowRates;

    public ValveSystem(List<String> puzzle) {
        valves = initValves(puzzle);
        connections = initConnections(puzzle);
        start = valves.get("AA".hashCode());
        valvesWithFlowRates = findValvesWithFlowRates();
        valvesWithFlowRates.add(start);
    }

    private Map<Integer, Valve> initValves(List<String> puzzle) {
        return puzzle.stream()
                .map(this::initAValve)
                .collect(Collectors.toMap(Valve::hashCode, valve -> valve));
    }

    private Valve initAValve(String line) {
        String[] reportLineParts = line.split("; ");
        String label = extractLabels(reportLineParts[0], 2).get(0);
        int flowRate = Integer.parseInt(reportLineParts[0].split("=")[1]);
        return new Valve(label, flowRate);
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

    private Set<Valve> findValvesWithFlowRates() {
        return valves.values().stream()
                .filter(valve -> valve.getFlowRate() != 0)
                .collect(Collectors.toSet());
    }

    public Map<ValveCombination, Integer> findValveCombinationsWithMaxPressure(int availableTime) {
        Map<ValveCombination, Integer> maxPressures = new HashMap<>();
        Map<ValvePair, Integer> shortestPaths = findShortestPaths();
        Deque<ValveSystemState> valveSystemStates = new ArrayDeque<>();
        ValveSystemState initState = new ValveSystemState(start, 0, 0, 0, Set.of());
        valveSystemStates.offer(initState);
        while (!valveSystemStates.isEmpty()) {
            ValveSystemState currentState = valveSystemStates.poll();
            for (Valve valve : valvesWithFlowRates) {
                if (!currentState.getValve().equals(valve) && !start.equals(valve)) {
                    // equivalent with minutes to reach here
                    int numberOfStepsFromCurrent = shortestPaths.get(new ValvePair(currentState.getValve(), valve));
                    if (!currentState.getOpenValves().contains(valve) && currentState.getElapsedTime() + numberOfStepsFromCurrent < availableTime) {
                        ValveSystemState newState = createState(currentState, valve, numberOfStepsFromCurrent);
                        valveSystemStates.offer(newState);
                        refreshValveCombinations(maxPressures, newState, availableTime);
                    }
                }
            }
        }
        return maxPressures;
    }

    private Map<ValvePair, Integer> findShortestPaths() {
        Map<ValvePair, Integer> shortestPaths = new HashMap<>();
        for (Valve from : valvesWithFlowRates) {
            for (Valve to : valvesWithFlowRates) {
                if (!from.equals(to) && !to.equals(start)) {
                    ValvePair valvePair = new ValvePair(from, to);
                    shortestPaths.put(valvePair, findShortestPath(valvePair));
                }
            }
        }
        return shortestPaths;
    }

    private int findShortestPath(ValvePair valvePair) {
        Set<Valve> visitedValves = new HashSet<>();
        int shortestPathLength = 0;
        Deque<ValveState> valveStates = new ArrayDeque<>();
        valveStates.offer(new ValveState(valvePair.getFrom(), 0));
        visitedValves.add(valvePair.getFrom());
        while (!valveStates.isEmpty()) {
            ValveState current = valveStates.poll();
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

    private void refreshValveCombinations(Map<ValveCombination, Integer> maxPressures, ValveSystemState newState, int availableTime) {
        int possibleReleasedPressure = getPossibleReleasedPressure(newState, availableTime);
        ValveCombination valveCombination = new ValveCombination(Collections.unmodifiableSet(newState.getOpenValves()));
        if (!(maxPressures.containsKey(valveCombination) && maxPressures.get(valveCombination) >= possibleReleasedPressure)) {
            maxPressures.put(valveCombination, possibleReleasedPressure);
        }
    }

    private int getPossibleReleasedPressure(ValveSystemState currentState, int availableTime) {
        int remainingTime = availableTime - currentState.getElapsedTime();
        int currentReleasedPressure = currentState.getReleasedPressure();
        while (remainingTime > 0) {
            currentReleasedPressure += currentState.getPressureRate();
            remainingTime--;
        }
        return currentReleasedPressure;
    }

    private ValveSystemState createState(ValveSystemState current, Valve valve, int numberOfStepsFromCurrent) {
        int elapsedTime = current.getElapsedTime() + numberOfStepsFromCurrent + 1;
        int pressureRate = current.getPressureRate() + valve.getFlowRate();
        int releasedPressure = current.getReleasedPressure() + (numberOfStepsFromCurrent + 1) * current.getPressureRate();
        Set<Valve> openValves = new HashSet<>(current.getOpenValves());
        openValves.add(valve);
        return new ValveSystemState(valve, elapsedTime, pressureRate, releasedPressure, Collections.unmodifiableSet(openValves));
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
