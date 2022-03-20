package bogdanov.plot.data;

import java.io.File;

public class Plot {

    private DataTable table;
    private byte[][] plotImage;

    public Plot(DataTable data){}

    public DataTable getDataTable(){
        return table;
    }

    public void setDataTable(DataTable table) {
        this.table = table;
        makePlotImage();
    }

    public byte[][] getPlotImage(){
        return plotImage;
    };

    public void setPlotImage(byte[][] plotImage) {
        this.plotImage = plotImage;
    }

    private void makePlotImage(){

    }

}
