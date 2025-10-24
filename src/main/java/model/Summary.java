package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Summary implements Serializable {
    private int userId;
    private int categoryId;
    private int creditFlag;
    private int purposeFlag;
    private int total;
    private LocalDate date;
    private String entryIds; // 日ごとの集計に含まれるEntryIDのカンマ区切り

    // getter / setter
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public int getCreditFlag() { return creditFlag; }
    public void setCreditFlag(int creditFlag) { this.creditFlag = creditFlag; }

    public int getPurposeFlag() { return purposeFlag; }
    public void setPurposeFlag(int purposeFlag) { this.purposeFlag = purposeFlag; }

    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getEntryIds() { return entryIds; }
    public void setEntryIds(String entryIds) { this.entryIds = entryIds; }
}
