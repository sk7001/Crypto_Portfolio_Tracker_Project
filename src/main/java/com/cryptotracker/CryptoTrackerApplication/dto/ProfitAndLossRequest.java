package com.cryptotracker.CryptoTrackerApplication.dto;

/**
 * Data Transfer Object for receiving Profit and Loss data from client requests.
 */
public class ProfitAndLossRequest {

    private Double profit;
    private Double loss;

    // Default constructor needed for deserialization
    public ProfitAndLossRequest() {}

    // Parameterized constructor for easier instantiation
    public ProfitAndLossRequest(Double profit, Double loss) {
        this.profit = profit;
        this.loss = loss;
    }

    // Getter for profit
    public Double getProfit() {
        return profit;
    }

    // Setter for profit
    public void setProfit(Double profit) {
        this.profit = profit;
    }

    // Getter for loss
    public Double getLoss() {
        return loss;
    }

    // Setter for loss
    public void setLoss(Double loss) {
        this.loss = loss;
    }
}