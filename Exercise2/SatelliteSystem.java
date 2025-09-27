/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SatelliteSystem;

/**
 *
 * @author nivet
 */
import java.util.*;

// ================= ENUM =================
enum Orientation {
    NORTH, SOUTH, EAST, WEST
}

// ================= OBSERVER =================
interface SatelliteObserver {
    void update(String event, Satellite satellite);
}

class GroundControl implements SatelliteObserver {
    private final String name;

    public GroundControl(String name) {
        this.name = name;
    }

    @Override
    public void update(String event, Satellite satellite) {
        System.out.println("[GroundControl-" + name + "] Event: " + event +
                " | Status: " + satellite.getStatus());
    }
}

// ================= SATELLITE (SINGLETON) =================
class Satellite {
    private static Satellite instance;
    private Orientation orientation;
    private boolean panelsActive;
    private int dataCollected;
    private double battery;
    private boolean sleepMode;
    private final List<SatelliteObserver> observers = new ArrayList<>();

    private Satellite() {
        this.orientation = Orientation.NORTH;
        this.panelsActive = false;
        this.dataCollected = 0;
        this.battery = 100.0;
        this.sleepMode = false;
    }

    public static Satellite getInstance() {
        if (instance == null) {
            instance = new Satellite();
        }
        return instance;
    }

    // Observer methods
    public void addObserver(SatelliteObserver obs) {
        observers.add(obs);
    }

    private void notifyObservers(String event) {
        for (SatelliteObserver obs : observers) {
            obs.update(event, this);
        }
    }

    // Core operations
    public void rotate(Orientation newOrientation) {
        if (sleepMode) {
            System.out.println("Satellite in sleep mode. Cannot rotate.");
            return;
        }
        this.orientation = newOrientation;
        consumeBattery(1.0);
        System.out.println("Rotated to " + newOrientation);
    }

    public void activatePanels() {
        if (!panelsActive) {
            panelsActive = true;
            consumeBattery(0.5);
            System.out.println("Solar panels activated.");
        } else {
            System.out.println("Panels already active.");
        }
    }

    public void deactivatePanels() {
        if (panelsActive) {
            panelsActive = false;
            System.out.println("Solar panels deactivated.");
        } else {
            System.out.println("Panels already inactive.");
        }
    }

    public void collectData() {
        if (sleepMode) {
            System.out.println("Satellite in sleep mode. Cannot collect data.");
            return;
        }
        if (!panelsActive) {
            System.out.println("Activate panels first!");
            return;
        }
        if (dataCollected >= 100) {
            System.out.println("Storage full! Transmit data first.");
            notifyObservers("DATA_FULL");
            return;
        }
        dataCollected += 10;
        consumeBattery(2.0);
        System.out.println("Collected 10 units. Total data: " + dataCollected);
    }

    public void transmitData() {
        if (dataCollected == 0) {
            System.out.println("No data to transmit.");
            return;
        }
        System.out.println("Transmitting " + dataCollected + " units to ground...");
        dataCollected = 0;
        consumeBattery(5.0);
        notifyObservers("DATA_TRANSMITTED");
    }

    public void enterSleepMode() {
        if (!sleepMode) {
            sleepMode = true;
            System.out.println("Satellite entered sleep mode (low power consumption).");
        } else {
            System.out.println("Already in sleep mode.");
        }
    }

    public void exitSleepMode() {
        if (sleepMode) {
            sleepMode = false;
            System.out.println("Satellite exited sleep mode.");
        } else {
            System.out.println("Not in sleep mode.");
        }
    }

    private void consumeBattery(double amount) {
        battery -= amount;
        if (battery <= 20 && battery > 0) {
            notifyObservers("LOW_BATTERY");
        }
        if (battery <= 0) {
            battery = 0;
            notifyObservers("BATTERY_DEPLETED");
            System.out.println("Battery depleted! Satellite shutting down...");
            System.exit(0);
        }
    }

    public String getStatus() {
        return String.format("Orientation: %s | Panels: %s | Data: %d | Battery: %.1f%% | Mode: %s",
                orientation,
                panelsActive ? "Active" : "Inactive",
                dataCollected,
                battery,
                sleepMode ? "Sleep" : "Active");
    }
}

// ================= MAIN APP =================
public class SatelliteSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Satellite sat = Satellite.getInstance();
        sat.addObserver(new GroundControl("Houston"));

        while (true) {
            printMenu();
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter orientation (NORTH, SOUTH, EAST, WEST): ");
                    String dir = sc.nextLine().toUpperCase();
                    try {
                        sat.rotate(Orientation.valueOf(dir));
                    } catch (Exception e) {
                        System.out.println("Invalid orientation!");
                    }
                    break;
                case 2: sat.activatePanels(); break;
                case 3: sat.deactivatePanels(); break;
                case 4: sat.collectData(); break;
                case 5: sat.transmitData(); break;
                case 6: sat.enterSleepMode(); break;
                case 7: sat.exitSleepMode(); break;
                case 8: System.out.println(sat.getStatus()); break;
                case 9:
                    System.out.println("Exiting system...");
                    System.exit(0);
                    break;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n=== Satellite Command System ===");
        System.out.println("1. Rotate Satellite");
        System.out.println("2. Activate Solar Panels");
        System.out.println("3. Deactivate Solar Panels");
        System.out.println("4. Collect Data");
        System.out.println("5. Transmit Data");
        System.out.println("6. Enter Sleep Mode");
        System.out.println("7. Exit Sleep Mode");
        System.out.println("8. Show Status");
        System.out.println("9. Exit");
        System.out.print("Enter choice: ");
    }
}

