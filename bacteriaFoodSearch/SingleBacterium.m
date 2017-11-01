function bacterium = SingleBacterium(Terrain)
bac.xcord=roundn(unifrnd(18,23),-1);
bac.ycord=roundn(unifrnd(21,26),-1);
while (Terrain.obs(round(bac.xcord/Terrain.reso)+301, round(bac.ycord/Terrain.reso)+301)~=0)
    bac.xcord=roundn(unifrnd(18,23),-1);
    bac.ycord=roundn(unifrnd(21,26),-1);
end
plot(bac.xcord,bac.ycord,'p','MarkerSize', 12);
dis=@(x,y)(x^2+y^2)^0.5;
alpha=0.001;
kk=30;
bac.theta=unifrnd(0,2*pi);
c=Terrain.ActualCon(round(bac.xcord/Terrain.reso)+301, round(bac.ycord/Terrain.reso)+301);
dc=0;
t=0;
%dt=0.5;
dt=exp(alpha*(dis(bac.xcord, bac.ycord)^2-kk^2));
st=0;
    while c <= 0.999*Terrain.ActualCon(301,301) 
     plot(bac.xcord,bac.ycord,'.');
     %comet(bac.xcord,bac.ycord);
     %p=[t bac.xcord  bac.ycord c bac.theta dc];
     %disp(dt);       
     bac.theta=bac.theta+normrnd(0, pi*(dc<=0));
     %quiver(bac.xcord,bac.ycord,dt*cos(bac.theta),dt*sin(bac.theta));
     %at edge
     if abs(roundn(bac.xcord+dt*cos(bac.theta),-1))<=30
        bac.xcord=roundn(bac.xcord+dt*cos(bac.theta),-1);
     end
     if abs(roundn(bac.ycord+dt*sin(bac.theta),-1))<=30
        bac.ycord=roundn(bac.ycord+dt*sin(bac.theta),-1);
     end
     
     dc=Terrain.ActualCon(round(bac.xcord/Terrain.reso)+301, round(bac.ycord/Terrain.reso)+301)-c;
     c=Terrain.ActualCon(round(bac.xcord/Terrain.reso)+301, round(bac.ycord/Terrain.reso)+301);
     t=t+dt;
     dt=exp(alpha*(dis(bac.xcord, bac.ycord)^2-kk^2));
     st=st+1;
    end
    bacterium=st;
end