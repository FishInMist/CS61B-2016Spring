public class Planet{
    public Planet(double xP, double yP, double xV, double yV, double m, String img){
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }
    public Planet(Planet p){
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }
    public double G = 6.67E-11;
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    public double calcDistance(Planet t){
        double dx = t.xxPos - xxPos;
        double dy = t.yyPos - yyPos;
        double r = Math.pow((Math.pow(dx, 2) + Math.pow(dy, 2)), 0.5);
        return r;
    }
    public double calcForceExertedBy(Planet t){
        double r = calcDistance(t);
        double F = G * mass * t.mass / (r * r);
        return F;
    }
    public double calcForceExertedByX(Planet t){
        double dx = t.xxPos - xxPos;
        return calcForceExertedBy(t) * (dx / calcDistance(t));
    }
    public double calcForceExertedByY(Planet t){
        double dy = t.yyPos - yyPos;
        return calcForceExertedBy(t) * (dy / calcDistance(t));
    }
    public double calcNetForceExertedByX(Planet[] allplanet){
        double Fnetx = 0;
        for (int i = 0; i < allplanet.length; i = i + 1){
            if (!this.equals(allplanet[i])){
            Fnetx = Fnetx + calcForceExertedByX(allplanet[i]);
        }
        }
        return Fnetx;
    }
    public double calcNetForceExertedByY(Planet[] allplanet){
        double Fnety = 0;
        for (int i = 0; i < allplanet.length; i = i + 1){
            if (!this.equals(allplanet[i])){
            Fnety = Fnety + calcForceExertedByY(allplanet[i]);
        }
        }
        return Fnety;
    }
    public void update(double dt, double fX, double fY){
        double anetx = fX / mass;
        double anety = fY / mass;
        xxVel = xxVel + anetx * dt;
        yyVel = yyVel + anety * dt;
        xxPos = xxPos + xxVel * dt;
        yyPos = yyPos + yyVel * dt;
    }
    public void draw(){
        StdDraw.picture(xxPos/1E10, yyPos/1E10, imgFileName);
    }
}