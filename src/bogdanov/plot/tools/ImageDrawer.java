package bogdanov.plot.tools;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ImageDrawer {

    private BufferedImage image = null;
    private Dimension size = null;
    private Image resizedImage = null;
    private int currentZoom = 1;
    private int type;

    public ImageDrawer(BufferedImage image) {
        setImage(image);
    }

    public ImageDrawer(Image image) {
        setImage(image);
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        if (image != null) {
            System.out.println("Image was SET");
            this.image = image;
            size = new Dimension(image.getWidth(), image.getHeight());
            type = image.getType();
            currentZoom = 1;
            resizedImage = fullCopy(image);
        }
    }

    public void setImage(Image image) {
        setImage((BufferedImage) image);
    }

//    private void updateResizedImage(byte[] byteImage) {
//        byteResizedImage = byteImage.clone();
//        resizedImage = new BufferedImage(size.width * currentZoom, size.height * currentZoom, type);
//        byte[] tmp = ((DataBufferByte) resizedImage.getRaster().getDataBuffer()).getData();
//        System.arraycopy(byteImage, 0, tmp, 0, byteImage.length);
//        resizedImageIcon = new ImageIcon(resizedImage);
//    }

    public void zoomOut() {
        if (image != null) {
            currentZoom = currentZoom > 1 ? --currentZoom : currentZoom < 0 ? --currentZoom : -2;
            update();
        }
    }

    public void zoomIn() {
        if (image != null) {
            currentZoom = currentZoom > 0 ? ++currentZoom : currentZoom < -2 ? ++currentZoom : 1;
            update();
        }
    }


    public void erasePoints(Point point) {
        changePoint(point, 0xffffffff);
    }

    public void erasePoints(Set<Point> points) {
        if (points != null) {
            for (Point p : points) {
                changePoint(p, 0xffffffff);
            }
        }
    }

    public void update() {
        if (currentZoom > 0) {
            resizedImage = image.getScaledInstance(size.width * currentZoom, size.height * currentZoom, Image.SCALE_FAST);
        } else {
            resizedImage = image.getScaledInstance(-size.width / currentZoom, -size.height / currentZoom, Image.SCALE_FAST);
        }
    }

    public void changePoint(Point point, int color) {
        image.setRGB(point.x, point.y, color);
    }

    public void changePoint(int x, int y, int color) {
        image.setRGB(x, y, color);
    }

    public void changePoint(Map<Point, Integer> pixels) {
        if (pixels != null) {
            Point p;
            for (Map.Entry<Point, Integer> e : pixels.entrySet()) {
                p = e.getKey();
                image.setRGB(p.x, p.y, e.getValue());
            }
        }
    }

    public Image getResizedImage() {
        return resizedImage;
    }

    public int getPixel(Point point) {
//        if (currentZoom > 0) {
//            return image.getRGB(point.x / currentZoom, point.y / currentZoom);
//        } else {
//            return image.getRGB(-point.x * currentZoom, -point.y * currentZoom);
//        }
        return image.getRGB(point.x, point.y);
    }

    public Point getPoint(Point point) {
        if (currentZoom > 0) {
            return new Point(point.x / currentZoom, point.y / currentZoom);
        } else {
            return new Point(-point.x * currentZoom, -point.y * currentZoom);
        }
    }

    public Set<Point> getPoint(Set<Point> points) {
        Set<Point> tmp = new HashSet<>();
        if (points != null) {
            for (Point p : points) {
                if (currentZoom > 0) {
                    tmp.add(getPoint(p));
                } else {
                    for (int i = 0; i < -currentZoom; i++) {
                        for (int j = 0; j < -currentZoom; j++) {
                            tmp.add(new Point(
                                    -p.x * currentZoom + i,
                                    -p.y * currentZoom + j
                            ));
                        }
                    }
                }
            }
        }
        return tmp;
    }

    public Set<Point> getResizedPoints(Set<Point> points) {
        Set<Point> newPoints = new HashSet<>();
        if (points != null) {
            if (currentZoom > 0) {
                for (Point p : points) {
                    for (int i = 0; i < currentZoom; i++) {
                        for (int j = 0; j < currentZoom; j++) {
                            newPoints.add(new Point(
                                    p.x * currentZoom + i,
                                    p.y * currentZoom + j
                            ));
                        }
                    }
                }
            } else {
                currentZoom *= -1;
                for (Point p : points) {
                    newPoints.add(new Point(
                            p.x / currentZoom,
                            p.y / currentZoom
                    ));
                }
                currentZoom *= -1;
            }
        }
        return newPoints;
    }

    public Point getResizedCoordinate(Point point) {
        if (point != null) {
            if (currentZoom > 0) {
                Set<Point> newPoints = new HashSet<>();
                return new Point(
                        point.x / currentZoom,
                        point.y / currentZoom
                );
            } else {
                return new Point(
                        -point.x * currentZoom,
                        -point.y * currentZoom
                );
            }
        }
        return null;
    }


    private BufferedImage fullCopy(BufferedImage image) {
        ColorModel cm = image.getColorModel();
        return new BufferedImage(cm, image.copyData(null), cm.isAlphaPremultiplied(), null);
    }

    public int getCurrentZoom() {
        return currentZoom;
    }

//    public void setCurrentZoom(int n) {
//        if (n != 0 && n != 1 && n != -1) {
//            if (currentZoom > n) {
//                int count = 1;
//                while (currentZoom > n) {
//                    n = n > 0 ? n << 1 : n < -2 ? n >> 1 : 1;
//                    count++;
//                }
//                for (int i = 0; i < count; i++) {
//                    resizeHalf();
//                }
//            } else if (currentZoom < n) {
//                int count = 1;
//                while (currentZoom < n) {
//                    n = n > 1 ? n >> 1 : n < 0 ? n << 1 : -2;
//                    count++;
//                }
//                for (int i = 0; i < count; i++) {
//                    resizeDouble();
//                }
//            }
//        } else {
//            currentZoom = 1;
//            updateResizedImage(byteImage);
//        }
//    }

}
