package bogdanov.plot.tools;

class ColourSubstitute {

    private int a, b, c;
    private double p, py;
    private int count = 1;

    ColourSubstitute(int rgba, int y) {
        a = ((rgba >> 16) & 0xff);
        b = ((rgba >> 8) & 0xff);
        c = (rgba & 0xff);
        if (a >= 200 && b >= 200 && c >= 200) {
            a = b = c = 0;
            count--;
        } else {
            c = c > a ? (c > b ? c : b) : (a > b ? a : b);
            c = c < 128 ? 0xff - c : c;
        }
        p = c;
        py = c * y;
    }

    void add(ColourSubstitute cs) {
        if (cs.c > 0) {
            this.py += cs.py;
            this.p = (this.p * count + cs.p) / ++count;
        }
    }

    int getY() {
        return (int) Math.round(py / p / count);
    }
}
