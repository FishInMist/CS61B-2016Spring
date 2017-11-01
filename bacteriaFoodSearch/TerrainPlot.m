clear all;
reso=0.1;
Terrain.reso=reso;
[X,Y]=meshgrid(-30:reso:30);
BaseCon=@(x,y)(200/(2*pi*1000))*exp(-(x.^2+y.^2)/(2*1000));
period =4;
A=0.05;
B=0.01;
Obstacles = @(x,y) min(0,-A*cos(pi.*(x)./period).*cos(pi.*(y)./period)+B);
Z0=BaseCon(X,Y);
Z1=Obstacles(X,Y);
[minxID, minyID]=find(Z1==min(min(Z1)));
[windX, windY]=meshgrid(-period/(2*reso):(period/(2*reso)),-period/(2*reso):(period/(2*reso)));% meshgrid for span of a single obstacle
ratio=0.3; %ratio of removed obstacles
f=randsample(length(minxID),round(length(minxID)*ratio)); % randomly remove obstacles
for i=1:length(f)
    rem_indX=windX+(minxID(f(i)));
    rem_indX(rem_indX<1)=1;rem_indX(rem_indX>(2*30/reso))=2*30/reso; % clip between the range of terrain
    rem_indY=windY+(minyID(f(i)));
    rem_indY(rem_indY<1)=1;rem_indY(rem_indY>(2*30/reso))=2*30/reso; % clip between the range of terrain
    Z1(rem_indX,rem_indY)=0;
end
rem_indX=windX+301;
rem_indY=windY+301;
Z1(rem_indX,rem_indY)=0;
Terrain.obs=Z1;
%contour(X,Y,Z1);
Terrain.ActualCon=Z0+Z1;
contour(X,Y,Terrain.ActualCon);
hold on;
%SingleBacterium(Terrain);
%Swarm(Terrain);
Swarm_DGD(Terrain);
%{
for i = 1:5000
    %A=SingleBacterium(Terrain);
    disp(i);
    %C=Swarm(Terrain);
    C=Swarm_DGD(Terrain);
    numofstep(i)=C;
end

smax=max(numofstep);
smin=min(numofstep);
space=linspace(smin,smax,(smax-smin));
yy=hist(numofstep,space);
yy=yy/sum(yy);
aver=mean(numofstep);
dev=std(numofstep);
plot(space,yy);
xlabel('Steps');
ylabel('Percentage');
hold on;
%}