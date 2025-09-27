/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

// Strategy interface
interface TrafficStrategy {
    void controlTraffic();
}

// Concrete strategies
class NormalTrafficStrategy implements TrafficStrategy {
    @Override
    public void controlTraffic() {
        System.out.println("[Normal Hours] Traffic light changes every 60 seconds.");
    }
}

class RushHourTrafficStrategy implements TrafficStrategy {
    @Override
    public void controlTraffic() {
        System.out.println("[Rush Hours] Traffic light adapts based on car density.");
    }
}

class EmergencyTrafficStrategy implements TrafficStrategy {
    @Override
    public void controlTraffic() {
        System.out.println("[Emergency Mode] Emergency lane stays GREEN at all times!");
    }
}

// Context
class TrafficLightController {
    private TrafficStrategy strategy;

    public void setStrategy(TrafficStrategy strategy) {
        this.strategy = strategy;
    }

    public void executeControl() {
        if (strategy != null) {
            strategy.controlTraffic();
        } else {
            System.out.println("No strategy selected.");
        }
    }
}

// Test class
public class StrategyPatternDemo {
    public static void main(String[] args) {
        TrafficLightController controller = new TrafficLightController();

        // Normal mode
        controller.setStrategy(new NormalTrafficStrategy());
        controller.executeControl();

        // Rush hour
        controller.setStrategy(new RushHourTrafficStrategy());
        controller.executeControl();

        // Emergency mode
        controller.setStrategy(new EmergencyTrafficStrategy());
        controller.executeControl();
    }
}

