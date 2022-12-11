package day._11;

import day._11.operation.MathOperation;
import day._11.operation.Operator;
import day._11.operation.TestOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Monkey {

    private static final MathOperation DEFAULT_OPERATION = new MathOperation(Operator.DIVIDE, 3);
    private final int id;
    private int numberOfInspection;
    private final MathOperation mathOperation;
    private final TestOperation testOperation;
    private final List<Item> items;

    public Monkey(List<String> monkeyData) {
        id = Integer.parseInt(monkeyData.get(0).split(" ")[1].replace(":", ""));
        items = parseItems(monkeyData.get(1));
        mathOperation = new MathOperation(monkeyData.get(2));
        testOperation = new TestOperation(monkeyData.subList(3, monkeyData.size()));
    }

    private List<Item> parseItems(String itemsLine) {
        List<Item> initItems = new ArrayList<>();
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(itemsLine);
        while (matcher.find()) {
            initItems.add(new Item(matcher.group()));
        }
        return initItems;
    }

    public Item doMathOnItem(Item item) {
        numberOfInspection++;
        Item newItem = new Item(item.getWorryLevel());
        newItem.doMath(mathOperation);
        newItem.doMath(DEFAULT_OPERATION);
        return newItem;
    }

    public int doTestOnItem(Item item) {
        return item.doTest(testOperation);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public int getNumberOfInspection() {
        return numberOfInspection;
    }

    public List<Item> getItems() {
        return new ArrayList<>(items);
    }

    @Override
    public String toString() {
        return "Monkey " + id + ": " + items.stream().map(Item::toString).collect(Collectors.joining(", "));
    }
}
