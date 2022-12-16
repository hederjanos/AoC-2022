package day._16;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
                .map(line -> {
                    List<String> neighbourLabels = new ArrayList<>();
                    String[] reportLineParts = line.split("; ");
                    String label = extractLabels(reportLineParts[0], 2).get(0);
                    neighbourLabels.add(label);
                    neighbourLabels.addAll(extractLabels(reportLineParts[1], 2));
                    return neighbourLabels;
                })
                .collect(Collectors.toMap(
                        labels -> labels.get(0).hashCode(),
                        labels -> labels.subList(1, labels.size()).stream()
                                .map(String::hashCode)
                                .collect(Collectors.toSet()))
                );
    }

    public List<Valve> getNeighboursOfValve(int hashCode) {
        Valve valve = valves.get(hashCode);
        if (valve == null) {
            throw new IllegalArgumentException();
        } else {
            return connections.get(hashCode).stream().map(valves::get).collect(Collectors.toList());
        }
    }

    public Valve getStart() {
        return start;
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
