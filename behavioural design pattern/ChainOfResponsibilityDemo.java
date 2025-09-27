/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author nivet
 */
// Abstract Handler
abstract class SecurityHandler {
    protected SecurityHandler nextHandler;

    public void setNextHandler(SecurityHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract void handleRequest(String alert);
}

// Concrete Handlers
class MotionSensor extends SecurityHandler {
    @Override
    public void handleRequest(String alert) {
        System.out.println("[MotionSensor] Movement detected: " + alert);
        if (nextHandler != null) nextHandler.handleRequest(alert);
    }
}

class DoorSensor extends SecurityHandler {
    @Override
    public void handleRequest(String alert) {
        System.out.println("[DoorSensor] Checking if doors are locked...");
        if (nextHandler != null) nextHandler.handleRequest(alert);
    }
}

class CameraSystem extends SecurityHandler {
    @Override
    public void handleRequest(String alert) {
        System.out.println("[CameraSystem] Intruder image captured!");
        if (nextHandler != null) nextHandler.handleRequest(alert);
    }
}

class NotificationSystem extends SecurityHandler {
    @Override
    public void handleRequest(String alert) {
        System.out.println("[NotificationSystem] Sending alert to owner and police!");
    }
}

// Test class
public class ChainOfResponsibilityDemo {
    public static void main(String[] args) {
        // Create handlers
        SecurityHandler motion = new MotionSensor();
        SecurityHandler door = new DoorSensor();
        SecurityHandler camera = new CameraSystem();
        SecurityHandler notify = new NotificationSystem();

        // Chain setup
        motion.setNextHandler(door);
        door.setNextHandler(camera);
        camera.setNextHandler(notify);

        // Trigger alert
        motion.handleRequest("Movement at 02:15 AM");
    }
}

