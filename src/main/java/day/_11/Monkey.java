package day._11;

import day._11.operation.MathOperation;
import day._11.operation.Operator;
import day._11.operation.TestOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Monkey {

    private static MathOperation DEFAULT_OPERATION = new MathOperation(Operator.DIVIDE, 3);
    private int id;
    private int numberOfInspection;
    private MathOperation mathOperation;
    private TestOperation testOperation;
    private List<Item> items;

    public Monkey(List<String> monkeyData) {
        id =  Integer.parseInt(monkeyData.get(0).split(" ")[1].replace(":", ""));
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

}
