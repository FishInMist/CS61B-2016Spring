public class NBody{
    public static double readRadius(String fileName){
        In in = new In(fileName);
        in.readInt();
        double radius = in.readDouble();
        return radius;
    }
    public static Planet[] readPlanets(String fileName){
        In in = new In(fileName);
        int N = in.readInt();
        in.readDouble();
        Planet[] planets = new Planet[N];
        for (int i = 0; i < N; i = i + 1){
            planets[i] = new Planet(in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readString());
            /*System.out.println(planets[i]);*/
        }
        return planets;
    }
    public static void main(String[] args){
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = NBody.readRadius(filename);
        Planet[] planets = NBody.readPlanets(filename);
        for (double time = 0; time <= T; time = time  + dt){
            String background = "starfield.jpg";
            StdDraw.setScale(-(radius/1E10), (radius/1E10));
            StdDraw.picture(0, 0, background, (2*radius/1E10), (2*radius/1E10));
            double[] xForces = new double[planets.length];
            double[] yForces = new double[planets.length];
            for (int i = 0; i < planets.length; i = i + 1){
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
                planets[i].update(dt, xForces[i], yForces[i]);
                planets[i].draw();
            }
            StdDraw.show(10);
        }  
    }
}