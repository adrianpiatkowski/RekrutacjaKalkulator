import java.util.ArrayList;

public class Calculator {
    private ArrayList<String> listONP = new ArrayList<>();
    private ArrayList<String> copyList = new ArrayList<>();
    private ArrayList<String> operations = new ArrayList<>(4);
    private String input;

    public Calculator(String equation) {
        operations();
        input = equation;
        this.doOnp();
    }

    public String toString() {
      String output = "";
      for (String x : listONP) {
          output = output + x + " ";
      }
      return output;
    }

    public String calculate() {
        int component1 = 0;
        int component2 = 0;
        int temporaryValue = 0;
        int i = 0;

        while (i < listONP.size() && listONP.size() > 1) {
            if (isNumber(listONP.get(i))) {
                component2 = component1;
                component1 = Integer.parseInt(listONP.get(i));
                i++;
            } else {
                switch (listONP.get(i).charAt(0)) {
                    case '+':
                        temporaryValue = component2 + component1;
                        i = i - 2;
                        listONP.set(i, Integer.toString(temporaryValue));
                        listONP.remove(i + 1);
                        listONP.remove(i + 1);
                        i = 0;
                        break;
                    case '-':
                        temporaryValue = component2 - component1;
                        i = i - 2;
                        listONP.set(i, Integer.toString(temporaryValue));
                        listONP.remove(i + 1);
                        listONP.remove(i + 1);
                        i = 0;
                        break;
                    case '*':
                        temporaryValue = component2 * component1;
                        i = i - 2;
                        listONP.set(i, Integer.toString(temporaryValue));
                        listONP.remove(i + 1);
                        listONP.remove(i + 1);
                        i = 0;
                        break;
                    case '/':
                        if (component1 != 0){
                            temporaryValue = component2 / component1;
                            i = i - 2;
                            listONP.set(i, Integer.toString(temporaryValue));
                            listONP.remove(i + 1);
                            listONP.remove(i + 1);
                            i = 0;
                        }
                        else {
                            System.out.println("Dzielenie przez 0");
                            System.exit(1);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        return listONP.get(0);
    }

    public String getInput() {
        return input;
    }

    private void replaceComponent() {
        if (copyList.size() > 0) {
            listONP.add(copyList.get(copyList.size() - 1));
            copyList.remove(copyList.size() - 1);
        } else {
            System.out.println("Nic do przesuniecia");
        }
    }

    private void removeValues() {
        while (copyList.size() > 0) {
            listONP.add(copyList.get(copyList.size() - 1));
            copyList.remove(copyList.size() - 1);
        }
    }

    private void operations() {
        operations.add("+");
        operations.add("-");
        operations.add("*");
        operations.add("/");
    }

    private int prioritize(String operator) {
        switch (operator) {
            case "+":
                return 1;
            case "-":
                return 1;
            case "*":
                return 2;
            case "/":
                return 2;
            default:
                return 100;
        }
    }

    private void addOperations(int elem, String sign, int priority) {
        if (copyList.size() == 0) {
            copyList.add(sign);
        } else if (priority <= this.priority()) {
            while (priority <= this.priority()){
                this.replaceComponent();
            }
            copyList.add(sign);
        } else if (elem == this.input.length() - 1) {
            this.replaceComponent();
            copyList.add(sign);
        } else {
            copyList.add(sign);
        }
    }

    private Boolean isNumber(String param) {
        boolean onlyNumbers = true;
        for (int i = 0; i < param.length(); i++) {
            if (param.charAt(i) < '0' || param.charAt(i) > '9') {
                onlyNumbers = false;
            }
        }
        return onlyNumbers;
    }

    private int priority() {
        int maxPriority = 0;
        int currentPriority;
        for (String x : copyList) {
            currentPriority = prioritize(x);
            if (currentPriority > maxPriority) {
                maxPriority = currentPriority;
            }
            return maxPriority;
        }
        return maxPriority;
    }

    private void doOnp() {
        StringBuilder read = new StringBuilder();
        int priority;
        for (int i = 0; i < input.length(); i++) {
            int j = i + 1;
            String mark = input.substring(i, i + 1);
            if (input.charAt(i) >= '0' && input.charAt(i) <= '9') {
                read.append(mark);
                if (j < input.length()) {
                    if (input.charAt(j) < '0' || input.charAt(j) > '9') {
                        listONP.add(read.toString());
                        read = new StringBuilder();
                    }
                } else {
                    listONP.add(read.toString());
                    read = new StringBuilder();
                }
            }
            if (operations.contains(mark)) {
                if (i == input.length() - 1){
                    System.out.println("Błedne równanie - operator na końcu");
                    System.exit(0);
                }
                if (i == 0) {
                    System.out.println("Błedne równanie - operator na poczatku");
                    System.exit(0);
                }
                priority = prioritize(mark);
                switch (mark) {
                    case "+":
                        this.addOperations(i, mark, priority);
                        break;
                    case "*":
                        this.addOperations(i, mark, priority);
                        break;
                    case "-":
                        this.addOperations(i, mark, priority);
                        break;
                    case "/":
                        this.addOperations(i, mark, priority);
                        break;
                    default:
                        break;
                }
            }
            if (input.charAt(i) == ' ') {
                if (input.charAt(i - 1) >= '0' && input.charAt(i - 1) <= '9' &&
                        input.charAt(i - 1) >= '0' && input.charAt(i - 1) <= '9'){
                    System.out.println("Błedne równanie - brak znaku operacji");
                    System.exit(0);
                }
                if (operations.contains(input.charAt(i - 1)) &&
                        operations.contains(input.charAt(i + 1))){
                    System.out.println("Błedne równanie - dwie operacje po sobie");
                    System.exit(0);
                }
            }
        }
        this.removeValues();
    }
}
