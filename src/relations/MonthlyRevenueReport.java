package relations;

import java.math.BigDecimal;

public class MonthlyRevenueReport {
    private String hotelName;
    private BigDecimal totalRevenue;
    private BigDecimal totalExpenses;
    private BigDecimal profit;

    public MonthlyRevenueReport(String hotelName, BigDecimal totalRevenue, BigDecimal totalExpenses, BigDecimal profit) {
        this.hotelName = hotelName;
        this.totalRevenue = totalRevenue;
        this.totalExpenses = totalExpenses;
        this.profit = profit;
    }

    public String getHotelName() {
        return hotelName;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public BigDecimal getTotalExpenses() {
        return totalExpenses;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    @Override
    public String toString() {
        return "MonthlyRevenueReport{" +
                "hotelName='" + hotelName + '\'' +
                ", totalRevenue=" + totalRevenue +
                ", totalExpenses=" + totalExpenses +
                ", profit=" + profit +
                '}';
    }
}
