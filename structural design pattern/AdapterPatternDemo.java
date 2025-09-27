/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AdapterPatternDemo;

/**
 *
 * @author nivet
 */
// Target interface
interface PaymentProcessor {
    void pay(int amount);
}

// Adaptee 1 (existing system)
class PayPalGateway {
    public void makePayment(int dollars) {
        System.out.println("Payment of $" + dollars + " made via PayPal.");
    }
}

// Adaptee 2
class StripeGateway {
    public void processPayment(int cents) {
        System.out.println("Payment of $" + (cents/100) + " made via Stripe.");
    }
}

// Adapter for PayPal
class PayPalAdapter implements PaymentProcessor {
    private PayPalGateway paypal = new PayPalGateway();

    @Override
    public void pay(int amount) {
        paypal.makePayment(amount);
    }
}

// Adapter for Stripe
class StripeAdapter implements PaymentProcessor {
    private StripeGateway stripe = new StripeGateway();

    @Override
    public void pay(int amount) {
        stripe.processPayment(amount * 100); // convert dollars → cents
    }
}

// Test
public class AdapterPatternDemo {
    public static void main(String[] args) {
        PaymentProcessor paypal = new PayPalAdapter();
        paypal.pay(50);

        PaymentProcessor stripe = new StripeAdapter();
        stripe.pay(75);
    }
}
