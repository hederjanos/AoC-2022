package day._21;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class JobGraph {

    private final Map<Integer, AbstractJob> jobs;
    private final Map<Integer, List<Integer>> connections;

    public JobGraph(List<String> puzzle) {
        jobs = initJobs(puzzle);
        connections = initConnections(puzzle);
    }

    private Map<Integer, AbstractJob> initJobs(List<String> puzzle) {
        return puzzle.stream()
                .map(line -> {
                    String[] lineParts = line.split(": ");
                    String label = lineParts[0];
                    String operation = extractOperation(lineParts[1]);
                    if (operation.isEmpty()) {
                        return new ValueJob(label, Integer.parseInt(lineParts[1]));
                    } else {
                        return new MathJob(label, operation);
                    }
                })
                .collect(Collectors.toMap(AbstractJob::hashCode, valve -> valve));
    }

    private String extractOperation(String linePart) {
        String operation = "";
        for (int i = 0; i < linePart.length(); i++) {
            if (linePart.charAt(i) == '+') {
                operation = "+";
                break;
            } else if (linePart.charAt(i) == '-') {
                operation = "-";
                break;
            } else if (linePart.charAt(i) == '*') {
                operation = "*";
                break;
            } else if (linePart.charAt(i) == '/') {
                operation = "/";
                break;
            }
        }
        return operation;
    }

    private Map<Integer, List<Integer>> initConnections(List<String> puzzle) {
        return puzzle.stream()
                .map(line -> extractLabels(line, 4))
                .collect(Collectors.toMap(
                        labels -> labels.get(0).hashCode(),
                        labels -> labels.subList(1, labels.size()).stream()
                                .map(String::hashCode)
                                .collect(Collectors.toList()))
                );
    }

    private List<String> extractLabels(String linePart, int numberOfChars) {
        List<String> labels = new ArrayList<>();
        Pattern pattern = Pattern.compile("[a-z]{" + numberOfChars + "}");
        Matcher matcher = pattern.matcher(linePart);
        while (matcher.find()) {
            labels.add(matcher.group());
        }
        return labels;
    }

    public List<AbstractJob> sort() {
        Map<Integer, Set<Integer>> copy = new HashMap<>();
        connections.forEach((key, value) -> copy.put(key, new HashSet<>(value)));
        List<AbstractJob> sortedJobs = new ArrayList<>();
        Deque<AbstractJob> values = new ArrayDeque<>();
        jobs.values().forEach(abstractJob -> {
            Set<Integer> dependencies = copy.get(abstractJob.hashCode());
            if (dependencies.isEmpty()) {
                values.add(abstractJob);
            }
        });
        while (!values.isEmpty()) {
            AbstractJob job = values.poll();
            if (!sortedJobs.contains(job)) {
                sortedJobs.add(job);
            }
            Set<AbstractJob> dependents = getDependentsOfJob(job.hashCode());
            for (AbstractJob dependent : dependents) {
                Set<Integer> dependencies = copy.get(dependent.hashCode());
                if (!dependencies.isEmpty()) {
                    dependencies.remove(job.hashCode());
                }
                if (dependencies.isEmpty()) {
                    values.offer(dependent);
                }
            }
        }
        return sortedJobs;
    }

    public Set<AbstractJob> getDependentsOfJob(int hashCode) {
        AbstractJob job = jobs.get(hashCode);
        if (job == null) {
            throw new IllegalArgumentException();
        } else {
            return connections.keySet().stream()
                    .filter(key -> connections.get(key).contains(hashCode))
                    .map(jobs::get)
                    .collect(Collectors.toSet());
        }
    }

    public long resolveJobs(List<AbstractJob> sortedJobs) {
        for (AbstractJob sortedJob : sortedJobs) {
            if (sortedJob instanceof MathJob) {
                MathJob job = (MathJob) sortedJob;
                List<AbstractJob> dependencies = connections.get(sortedJob.hashCode()).stream()
                        .map(jobs::get).
                        collect(Collectors.toList());
                String operation = job.getOperation();
                long result;
                switch (operation) {
                    case "+":
                        result = dependencies.get(0).getValue() + dependencies.get(1).getValue();
                        break;
                    case "-":
                        result = dependencies.get(0).getValue() - dependencies.get(1).getValue();
                        break;
                    case "*":
                        result = dependencies.get(0).getValue() * dependencies.get(1).getValue();
                        break;
                    case "/":
                        result = dependencies.get(0).getValue() / dependencies.get(1).getValue();
                        break;
                    default:
                        throw new IllegalStateException();
                }
                MathJob newJob = new MathJob(job.getLabel(), operation, result);
                jobs.put(newJob.hashCode(), newJob);
            }
        }
        return jobs.get(("root").hashCode()).getValue();
    }

}
