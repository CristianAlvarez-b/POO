package ICPC;
import java.util.*;
import shapes.*;

/**
 * Write a description of class IceepeeceeContest here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class IceepeeceeContest
{
    private static Iceepeecee iceepeecee;
    static final double PI = Math.PI;
    //Using Iceepeecee
    public static Number solve(Number[][][] islands, Number[][][] flights) {
        Iceepeecee iceepeecee = new Iceepeecee(islands, flights);
        String[] allIslands = new String[islands.length];
        String[] temporal = new String[islands.length];
        allIslands = iceepeecee.islands();
        Arrays.sort(allIslands);
        double lo = 0.0;
        double hi = 90; 
        double best = -1;
        for (int rep = 0; rep < 64; rep++) {
            double th = (hi + lo) / 2;
            iceepeecee.photograph(th); 
            temporal = iceepeecee.observedIslands();
            Arrays.sort(temporal);
            boolean areEqual = Arrays.equals(allIslands, temporal);
            if (areEqual) {
                hi = th;
                best = th;
            } else {
                lo = th;
            }
        }
    
        if (best == -1) {
            System.out.println("Impossible");
            return -1;
        } else {
            Number theta = (best);
            return theta;
        }
    }
    
    public static void simulate(Number[][][] islands, Number[][][]fligths){
        iceepeecee = new Iceepeecee(islands, fligths);
        String [] allIslands = new String[islands.length];
        String [] temporal = new String[islands.length];
        allIslands = iceepeecee.islands();
        Arrays.sort(allIslands);
        double lo = 0.0;
        double hi = 90;
        double best = -1;
        for(int rep = 0; rep < 64; rep++){
            double th = (hi + lo) / 2;
            iceepeecee.makeInvisible(); 
            iceepeecee.photograph(th);
            temporal = iceepeecee.observedIslands();
            Arrays.sort(temporal);
            iceepeecee.makeVisible();
            boolean areEqual = Arrays.equals(allIslands, temporal);
            if(areEqual){
                hi = th; 
                best = th;
            }else{
                lo = th;
            }
        }
        
        if(hi == -1){
           iceepeecee.makeInvisible(); 
        }else{
           iceepeecee.makeInvisible(); 
           iceepeecee.photograph(best);
           iceepeecee.makeVisible();
        }
    }
    
    public static double solveExact(double[][][] islands, double[][][]fligths) {
        List<List<Point>> I = new ArrayList<>();
        for (int i = 0; i < islands.length; i++) {
            int NI = islands[0].length;
            List<Point> island = new ArrayList<>();
            for (int j = 0; j < NI; j++) {
                double x = islands[i][j][0];
                double y = islands[i][j][1];
                island.add(new Point(x, y));
            }
            I.add(island);
        }
        double lo = 0.0;
        double hi = PI / 2;
        List<Point> F1 = new ArrayList<>();
        List<Point> F2 = new ArrayList<>();
        List<Double> FZ1 = new ArrayList<>();
        List<Double> FZ2 = new ArrayList<>();
        for (int i = 0; i < fligths.length; i++) {
            double x1 = fligths[i][0][0];
            double y1 = fligths[i][0][1];
            double z1 = fligths[i][0][2];
            double x2 = fligths[i][1][0];
            double y2 = fligths[i][1][1];
            double z2 = fligths[i][1][2];
            F1.add(new Point(x1, y1));
            F2.add(new Point(x2, y2));
            FZ1.add(z1);
            FZ2.add(z2);
        }
        for (int rep = 0; rep < 64; rep++) {
            double th = (hi + lo) / 2;
            List<Boolean> seen = new ArrayList<>(islands.length);
            for (int i = 0; i < islands.length; i++) {
                seen.add(false);
            }

            for (int f = 0; f < fligths.length; f++) {
            List<Point> poly = new ArrayList<>();
            Point ortho = new Point(F1.get(f).y - F2.get(f).y, F2.get(f).x - F1.get(f).x);
            ortho = ortho.divide(ortho.len());
            poly.add(F1.get(f).subtract(ortho.multiply(FZ1.get(f) * Math.tan(th))));
            poly.add(F2.get(f).subtract(ortho.multiply(FZ2.get(f) * Math.tan(th))));
            poly.add(F2.get(f).add(ortho.multiply(FZ2.get(f) * Math.tan(th))));
            poly.add(F1.get(f).add(ortho.multiply(FZ1.get(f) * Math.tan(th))));
            double mxx = 1e7;
                for (Point point : poly) {
                    mxx = Math.max(mxx, point.x);
                }
                
                for (int i = 0; i < I.size(); i++) {
                    if (!seen.get(i)) {
                        boolean fail = false;
                        for (Point p : I.get(i)) {
                            int cnt = 0;
                            for (int j = 0; j < poly.size(); j++) {
                                Point a = poly.get(j);
                                Point b = poly.get((j + 1) % poly.size());
                                if (lineSegIntersection(a, b, p, new Point(mxx + 1337, p.y + 7331))) {
                                    cnt++;
                                }
                            }
                            if (cnt % 2 == 0) {
                                fail = true;
                                break;
                            }
                        }
                        if (!fail) {
                            seen.set(i, true);
                        }
                    }
                }
            }
            boolean allIslandsSeen = true;
            for (Boolean islandSeen : seen) {
                if (!islandSeen) {
                    allIslandsSeen = false;
                    break;
                }
            }

            if (allIslandsSeen) {
                hi = th;
            } else {
                lo = th;
            }
        }

        if (hi == PI / 2) {
            System.out.println("impossible");
            return(-1);
        } else {
            return((hi + lo) / 2 * 180 / PI);
        }
    }
    private static class Point {
        double x, y;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        Point subtract(Point p) {
            return new Point(x - p.x, y - p.y);
        }

        Point add(Point p) {
            return new Point(x + p.x, y + p.y);
        }

        Point multiply(double c) {
            return new Point(x * c, y * c);
        }

        Point divide(double c) {
            return new Point(x / c, y / c);
        }

        double len() {
            return Math.hypot(x, y);
        }
    }

    private static double dotProd(Point a, Point b) {
        return a.x * b.x + a.y * b.y;
    }

    private static double crossProd(Point a, Point b) {
        return a.x * b.y - a.y * b.x;
    }

    private static boolean lineSegIntersection(Point a1, Point a2, Point b1, Point b2) {
        double cp1 = crossProd(new Point(b2.x - b1.x, b2.y - b1.y), new Point(a1.x - b1.x, a1.y - b1.y));
        double cp2 = crossProd(new Point(b2.x - b1.x, b2.y - b1.y), new Point(a2.x - b1.x, a2.y - b1.y));
        if (cp1 > 0 && cp2 > 0) return false;
        if (cp1 < 0 && cp2 < 0) return false;
        cp1 = crossProd(new Point(a2.x - a1.x, a2.y - a1.y), new Point(b1.x - a1.x, b1.y - a1.y));
        cp2 = crossProd(new Point(a2.x - a1.x, a2.y - a1.y), new Point(b2.x - a1.x, b2.y - a1.y));
        if (cp1 > 0 && cp2 > 0) return false;
        if (cp1 < 0 && cp2 < 0) return false;
        return true;
    }
}



