package hcmut.spss.be.dtos.response;

public class PrintJobStats {
    private int dayOfWeek; // 1 = Chủ Nhật, 7 = Thứ Bảy
    private long totalPagesPrinted;

    // Constructor, getters và setters

    public PrintJobStats(int dayOfWeek, long totalPagesPrinted) {
        this.dayOfWeek = dayOfWeek;
        this.totalPagesPrinted = totalPagesPrinted;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public long getTotalPagesPrinted() {
        return totalPagesPrinted;
    }

    public void setTotalPagesPrinted(long totalPagesPrinted) {
        this.totalPagesPrinted = totalPagesPrinted;
    }
}
