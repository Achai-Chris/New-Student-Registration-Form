/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author Chris Pc
 */
import java.util.Scanner;

public class AuctionSystem {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        // Vehicle details
        System.out.print("Enter vehicle registration number: ");
        String regNumber = input.nextLine();

        System.out.print("Enter vehicle cost: ");
        double vehicleCost = input.nextDouble();

        System.out.print("Enter total deposits made: ");
        double deposits = input.nextDouble();

        System.out.print("Enter total expenses incurred: ");
        double expenses = input.nextDouble();

        // Bidder prices
        System.out.print("Enter Bidder 1 price: ");
        double bid1 = input.nextDouble();

        System.out.print("Enter Bidder 2 price: ");
        double bid2 = input.nextDouble();

        System.out.print("Enter Bidder 3 price: ");
        double bid3 = input.nextDouble();

        // Determine highest bidder
        double highestBid = bid1;
        int highestBidder = 1;

        if (bid2 > highestBid) {
            highestBid = bid2;
            highestBidder = 2;
        }

        if (bid3 > highestBid) {
            highestBid = bid3;
            highestBidder = 3;
        }

        // Balance and profit/loss calculation
        double balance = highestBid - deposits;
        double profitOrLoss = highestBid - (vehicleCost + expenses);

        // Display results
        System.out.println("\n--- AUCTION RESULTS ---");
        System.out.println("Vehicle Registration: " + regNumber);
        System.out.println("Highest Bidder: Bidder " + highestBidder);
        System.out.println("Highest Bid Price: " + highestBid);
        System.out.println("Balance Left: " + balance);

        if (profitOrLoss > 0) {
            System.out.println("Profit Made: " + profitOrLoss);
        } else {
            System.out.println("Loss Made: " + Math.abs(profitOrLoss));
        }

        // After clearing balance
        double finalProfit = highestBid - (vehicleCost + expenses);

        System.out.println("\nAfter balance is cleared:");
        if (finalProfit > 0) {
            System.out.println("Final Profit: " + finalProfit);
        } else {
            System.out.println("Final Loss: " + Math.abs(finalProfit));
        }

        input.close();
    }
}
