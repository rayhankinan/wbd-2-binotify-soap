package binotify.model;
import java.util.List;

public class DataPagination {
    private int pageCount;
    private List<Subscribe> data;

    public int getPageCount() {
        return pageCount;
    }
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<Subscribe> getData() {
        return data;
    }

    public void setData(List<Subscribe> data) {
        this.data = data;
    }

    public void addData(Subscribe subscribe) {
        this.data.add(subscribe);
    }
}
