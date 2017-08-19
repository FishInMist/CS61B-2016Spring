public class TestPlanetConstructor{
    public static void main(String[] args){
        Planet Samh = new Planet(1, 0, 3.3, 4.4, 10, "Samh");
        Planet AEgir = new Planet(3, 3, 3.3, 4.4, 5, "AEgir");
        Planet Rocinante = new Planet(5, -3, 3.3, 4.4, 50, "Rocinante");
        double F1x = Samh.calcForceExertedByX(AEgir);
        double F1y = Samh.calcForceExertedByY(AEgir);
        double F2x = Samh.calcForceExertedByX(Rocinante);
        double F2y = Samh.calcForceExertedByY(Rocinante);
        Planet[] allplanet = {Samh, AEgir, Rocinante};
        System.out.println(Samh.calcNetForceExertedByX(allplanet));
        System.out.println(Samh.calcNetForceExertedByY(allplanet));
    }
}