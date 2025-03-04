package prog2.ha1.testing;

// behaviour inspired by https://www.online-calculator.com/
public class Calculator {

    private String screen = "0";

    private double latestValue;

    private String latestOperation = "";

    public String readScreen() { // was steht jetzt auf dem Bildschirm
        return screen;
    }

    public void pressDigitKey(int digit) { // also die Tasten 0-9
        if(digit > 9 || digit < 0) throw new IllegalArgumentException();

        if(screen.equals("0")) screen = "";

        if(latestOperation.isEmpty()) {
            screen = screen + digit;
        } else {
            latestValue = Double.parseDouble(screen);
            screen = Integer.toString(digit);
        }
    }

    public void pressClearKey() { // die Taste CE
        screen = "0";
        latestOperation = "";
        latestValue = 0.0;
    }

    public void pressBinaryOperationKey(String operation)  { // also die Tasten /,x,-,+
        latestOperation = operation;
    }

    public void pressUnaryOperationKey(String operation) { // also die Tasten Wurzel, %, 1/x
        var result = switch(operation){
            case "%" -> Double.parseDouble(screen) / 100.0;
            default -> throw new IllegalArgumentException();
        };
        screen = Double.toString(result);
    }

    public void pressDotKey() { // die Komma- bzw. Punkt-Taste
        if(!screen.endsWith(".")) screen = screen + ".";
    }

    public void pressNegativeKey() { // die +/- Taste
        screen = screen.startsWith("-") ? screen.substring(1) : "-" + screen;
    }

    public void pressEqualsKey() { // die Taste =
        String result = switch(latestOperation) {
            case "+" -> Double.toString(latestValue + Double.parseDouble(screen));
            case "-" -> Double.toString(latestValue - Double.parseDouble(screen));
            case "x" -> Double.toString(latestValue * Double.parseDouble(screen));
            case "/" -> dividing(latestValue);
            default -> throw new IllegalArgumentException();
        };
        screen = result;
        if(screen.endsWith(".0")) screen = screen.substring(0,screen.length()-2);

    }

    public String dividing(double latestValue){
        if(Double.parseDouble(screen)==0){
            return "Error";
        }else{
            return Double.toString(latestValue / Double.parseDouble(screen));
        }

    }
}