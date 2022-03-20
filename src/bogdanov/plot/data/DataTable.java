package bogdanov.plot.data;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class DataTable {

    private int size = 64;
    private double[][] table = new double[size][2];
    private int end = 0;

    private String name = null;
    private String xLabel = null;
    private String yLabel = null;
    private String xUnits = null;
    private String yUnits = null;

    public DataTable() {
    }

    public DataTable(String name) {
        this.name = name;
    }

    public DataTable(DataTable table) {
        this.size = table.size;
        this.end = table.end;
        this.table = table.table;

        {
            if (table.name.matches(".*\\sCopy(\\d*)")) {
                int n = Integer.parseInt(table.name.substring(
                        table.name.lastIndexOf(" Copy(") + 6,
                        table.name.length() - 2
                ));
                this.name = table.name.substring(0, table.name.lastIndexOf(" Copy(") - 1);
                this.name = String.format("%s Copy(%d)", this.name, n++);
            } else {
                this.name = table.name + " Copy(1)";
            }
        }
        this.xLabel = table.xLabel;
        this.yLabel = table.yLabel;
        this.xUnits = table.xUnits;
        this.yUnits = table.yUnits;
    }


    public int getSize() {
        return end;
    }

    public void setSize(int size) {
        this.size = size;
        double[][] tmp = table;
        table = new double[size][2];
        for (int i = 0; i < size; i++) {
            table[i] = tmp[i];
        }
    }

    public double[][] getTable() {
        return Arrays.copyOf(table, end);
    }

    public void setTable(double[][] table) {
        this.table = table;
        size = table.length;
        end = size;
    }

    public void setTable(Map<Double, Double> table) {
        this.size = size;
        end = size;
        int i = 0;
        for (Map.Entry<Double, Double> e : table.entrySet()) {
            this.table[i][0] = e.getKey();
            this.table[i][1] = e.getValue();
        }
    }

    public void setTable(DataTable table) {
        this.size = table.size;
        this.end = table.end;
        this.table = table.table;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getXLabel() {
        return xLabel;
    }

    public void setXLabel(String xLabel) {
        this.xLabel = xLabel;
    }

    public String getYLabel() {
        return yLabel;
    }

    public void setYLabel(String yLabel) {
        this.yLabel = yLabel;
    }

    public String getXUnits() {
        return xUnits;
    }

    public void setXUnits(String xUnits) {
        this.xUnits = xUnits;
    }

    public String getYUnits() {
        return yUnits;
    }

    public void setYUnits(String yUnits) {
        this.yUnits = yUnits;
    }


    public void add(double x, double y) {
        if (end == size) {
            increaseSize();
        }
        table[end][0] = x;
        table[end][1] = y;
        end++;
    }

    public void add(double[] point) {
        if (point.length == 2) {
            if (end == size) {
                increaseSize();
            }
            table[end][0] = point[0];
            table[end][1] = point[1];
            end++;
        }
    }

    public void add(double[][] table) {
        while (end + table.length > size) {
            increaseSize();
        }
        for (int i = 0; i < table.length; i++) {
            if (table[i].length == 2) {
                this.table[end++] = table[i];
            }
        }
    }

    public void add(Map<Double, Double> table) {
        while (end + table.size() > size) {
            increaseSize();
        }
        for (Map.Entry<Double, Double> e : table.entrySet()) {
            this.table[end][0] = e.getKey();
            this.table[end][1] = e.getValue();
            end++;
        }
    }

    public void resize(double leftBorder, double rightBorder, double step) {
        System.out.println("Table length before sort = " + table.length);
        sort();
        System.out.println("Table length after sort = " + table.length);
        if (leftBorder > rightBorder) {
            double tmp = leftBorder;
            leftBorder = rightBorder;
            rightBorder = tmp;
        }

        while (leftBorder < table[0][0]) {
            leftBorder += step;
        }
        while (rightBorder > table[size - 1][0]) {
            rightBorder -= step;
        }
        double[][] resizedTable = new double[(int) ((rightBorder - leftBorder) / step) + 1][2];
        double x;
        int j = 1;
        for (int i = 0; i < resizedTable.length; i++) {
            x = leftBorder + i * step;
            while (x > table[j][0]) {
                j++;
            }
            resizedTable[i][0] = x;
            resizedTable[i][1] = extrapolatePoint(x, j);
        }
        size = resizedTable.length;
        end = size;
        table = resizedTable;
    }

    public void resize(double leftBorder, double rightBorder, int numberOfSteps) {
        resize(leftBorder, rightBorder, (rightBorder - leftBorder) / numberOfSteps);
    }

    private double extrapolatePoint(double x, int index) {
        double x1 = table[index - 1][0];
        double y1 = table[index - 1][1];
        double x2 = table[index][0];
        double y2 = table[index][1];

        return ((y2 - y1) * x + (y1 * x2 - y2 * x1)) / (x2 - x1);
    }

    public void fillTable(Function<Integer, Double> x, Function<Double, Double> yFunction, int size) {
        while (size > this.size) {
            increaseSize();
        }
        end = size;
        for (int i = 0; i < size; i++) {
            table[i][0] = x.apply(i);
            table[i][1] = yFunction.apply(table[i][0]);
        }
    }

    public void sort() {
        size = end;
        table = Arrays.copyOf(table, size);
//        sort(0, size - 1);
        Arrays.sort(table, Comparator.comparingDouble(o -> o[0]));
    }

    private void sort(int start, int end) {
        if (start > end) {
            start = start ^ end;
            end = start ^ end;
            start = start ^ end;
        }
        if (end - start == 1) {
            if (table[start][0] > table[end][0]) {
                double[] tmp = table[start];
                table[start] = table[end];
                table[end] = table[start];
            }
        } else if (end - start > 1) {
            int middle = (start + end) >> 1;
            sort(start, middle);
            sort(middle + 1, end);
            double[] tmp;
            int l = start;
            int r = middle + 1;
            for (int i = start; i <= end; i++) {
                if (l <= middle && r <= end) {
                    if (table[l][0] <= table[r][0]) {
                        table[i] = table[l];
                        l++;
                    } else {
                        table[i] = table[r];
                        r++;
                    }
                } else if (r <= end) {
                    for (r = r; r <= end; r++) {
                        table[i++] = table[r];
                    }
                } else {
                    for (l = l; l <= middle; l++) {
                        table[i++] = table[l];
                    }
                }
            }
        }
    }

    private void increaseSize() {
        double[][] tmp = table.clone();
        size <<= 1;
        table = new double[size][2];
        for (int i = 0; i < tmp.length; i++) {
            table[i] = tmp[i];
        }
    }


    public void clearTable() {
        size = 64;
        end = 0;
        table = new double[size][2];
    }

    public void clearNames() {
        name = null;
        xLabel = null;
        yLabel = null;
        xUnits = null;
        yUnits = null;
    }

    public void clear() {
        clearTable();
        clearNames();
    }

    public boolean isEmpty() {
        return end == 0;
    }


    @Override
    public DataTable clone() {
        return new DataTable(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(String.format(
                "%s\n%s\t%s\n%s\t%s\n",
                name != null ? name : "",
                xLabel != null ? xLabel : "",
                yLabel != null ? yLabel : "",
                xUnits != null ? xUnits : "",
                yUnits != null ? yUnits : ""
        ));
        sort();
        for (int i = 0; i < end; i++) {
            sb.append(table[i][0]);
            sb.append("\t");
            sb.append(table[i][1]);
            sb.append("\n");
        }
//        for (double[] point : table) {
//            sb.append(point[0]);
//            sb.append("\t");
//            sb.append(point[1]);
//            sb.append("\n");
//        }
        return sb.toString();
    }

}
