/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Chris Pc
 */
import java.util.Scanner;

public class GradeSummary{

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        int count = 0;

        int grade1 = 0, grade2 = 0, grade3 = 0, grade4 = 0,
            grade5 = 0, grade6 = 0, grade7 = 0, grade8 = 0, grade9 = 0;

        while (count < 5) {

            System.out.print("Enter student's score (0 - 100): ");
            int score = input.nextInt();

            if (score >= 80 && score <= 100) {
                grade1++;
            } 
            else if (score >= 75) {
                grade2++;
            } 
            else if (score >= 66) {
                grade3++;
            } 
            else if (score >= 60) {
                grade4++;
            } 
            else if (score >= 50) {
                grade5++;
            } 
            else if (score >= 45) {
                grade6++;
            } 
            else if (score >= 35) {
                grade7++;
            } 
            else if (score >= 30) {
                grade8++;
            } 
            else if (score >= 0) {
                grade9++;
            } 
            else {
                System.out.println("Invalid score!");
                continue;
            }

            count++;
        }

        System.out.println("\nSummary:");
        System.out.println("Grade 1: " + grade1);
        System.out.println("Grade 2: " + grade2);
        System.out.println("Grade 3: " + grade3);
        System.out.println("Grade 4: " + grade4);
        System.out.println("Grade 5: " + grade5);
        System.out.println("Grade 6: " + grade6);
        System.out.println("Grade 7: " + grade7);
        System.out.println("Grade 8: " + grade8);
        System.out.println("Grade 9: " + grade9);
    }
}