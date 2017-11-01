function bacteria = Swarm_DGD(Terrain)
    numofbac=5;
    NA=zeros(numofbac);
    dis=@(x,y)(x^2+y^2)^0.5;
    dis1=@(x,y)((x(1)-y(1))^2+(x(2)-y(2))^2)^0.5;
    dis2=@(x)(x(1)^2+x(2)^2)^0.5;
    alpha=0.001;
    kk=30;
    RR=0.1;
    RO=4;
    RA=4.3;
    w=1.5;
    intera=1;
% initialize of swarm
    for i = 1:numofbac
        bac(i).cor=[roundn(unifrnd(18,19),-1) roundn(unifrnd(18,19),-1)];
        %avoid to put bacterium in obstacles;
        while (Terrain.obs(round(bac(i).cor(1)/Terrain.reso)+301, round(bac(i).cor(2)/Terrain.reso)+301)~=0)
            bac(i).cor=[roundn(unifrnd(18,19),-1) roundn(unifrnd(18,19),-1)];
        end
        plot(bac(i).cor(1),bac(i).cor(2),'p','MarkerSize', 12);
        bac(i).st=0;
        bac(i).theta=unifrnd(0,2*pi);
        bac(i).v=[cos(bac(i).theta) sin(bac(i).theta)];
        bac(i).c=Terrain.ActualCon(round(bac(i).cor(1)/Terrain.reso)+301, round(bac(i).cor(2)/Terrain.reso)+301);
        bac(i).dc=0;
        bac(i).dis=dis(bac(i).cor(1),bac(i).cor(2));
        bac(i).urr=[0 0];
        bac(i).uro=[0 0];
        bac(i).ura=[0 0];
        bac(i).dt=exp(alpha*(bac(i).dis^2-kk^2));
    end
    ST=bac(:).st;

% update agents' position
    while sum(NA)<numofbac
        for i=1:numofbac
        bac(i).urr=[0 0];
        bac(i).uro=[0 0];
        bac(i).ura=[0 0];
        if bac(i).c> 0.999*Terrain.ActualCon(301,301)
            NA(i)=1;
            bac(i).v=[0 0];
        end
        end
        %interaction force calculation
        for i = 1: numofbac
            if bac(i).c< 0.999*Terrain.ActualCon(301,301)
            plot(bac(i).cor(1),bac(i).cor(2),'.')
            for j=1:numofbac
                if bac(j).cor ~= bac(i).cor
                    if j~=i && dis1(bac(j).cor, bac(i).cor)<=RR
                        bac(i).urr=bac(i).urr-(bac(j).cor-bac(i).cor)/dis1(bac(i).cor, bac(j).cor);
                    else
                        bac(i).uro=bac(i).uro+exp(-2*dis1(bac(j).cor,bac(i).cor))*bac(j).v;
                        bac(i).ura=bac(i).ura+exp(-0.5*dis1(bac(j).cor,bac(i).cor))*(bac(j).cor-bac(i).cor)/dis1(bac(i).cor, bac(j).cor);
                    end
                end
            end
            bac(i).u=bac(i).urr+bac(i).uro+bac(i).ura;
            %A=[bac(i).urr bac(i).uro bac(i).ura];
            %A=[i bac(i).cor bac(i).u bac(i).dc];
            %disp(A);
            %{
            quiver(bac(i).cor(1),bac(i).cor(2),bac(i).v(1),bac(i).v(2),'color',[0 1 0]);
            if intera==1
                quiver(bac(i).cor(1),bac(i).cor(2),bac(i).u(1),bac(i).u(2),'color',[0 0 1]);%R G B   
            end
            %}
            end
        end

        %coordinate update
        for i = 1: numofbac           
     
            if bac(i).c< 0.999*Terrain.ActualCon(301,301)
          if intera==0   
            if abs(roundn(bac(i).cor(1)+bac(i).v(1)*bac(i).dt,-1))<=30
                bac(i).cor(1)=roundn(bac(i).cor(1)+bac(i).v(1)*bac(i).dt,-1);
            end
            if abs(roundn(bac(i).cor(2)+bac(i).v(2)*bac(i).dt,-1))<=30
                bac(i).cor(2)=roundn(bac(i).cor(2)+bac(i).v(2)*bac(i).dt,-1);
            end
          elseif intera==1
             if bac(i).u == [0 0]
               %quiver(bac(i).cor(1),bac(i).cor(2),(w*(dc>0))*bac(i).v(1)*bac(i).dt,(w*(dc>0))*bac(i).v(2)*bac(i).dt,'color',[1 0 0]);
               if abs(roundn(bac(i).cor(1)+(w)*bac(i).v(1)*bac(i).dt,-1))<=30
                  bac(i).cor(1)=roundn(bac(i).cor(1)+(w)*bac(i).v(1)*bac(i).dt,-1);
               end
               if abs(roundn(bac(i).cor(2)+(w)*bac(i).v(2)*bac(i).dt,-1))<=30
                  bac(i).cor(2)=roundn(bac(i).cor(2)+(w)*bac(i).v(2)*bac(i).dt,-1);
               end
             else
               %quiver(bac(i).cor(1),bac(i).cor(2),((bac(i).u(1)/dis2(bac(i).u))+(w*(dc>0))*bac(i).v(1))*bac(i).dt,((bac(i).u(2)/dis2(bac(i).u))+(w*(dc>0))*bac(i).v(2))*bac(i).dt,'color',[1 0 0]);
               if abs(roundn(bac(i).cor(1)+((bac(i).u(1))+(w*(bac(i).dc>=0))*bac(i).v(1))*bac(i).dt,-1))<=30
                  bac(i).cor(1)=roundn(bac(i).cor(1)+((bac(i).u(1))+(w*(bac(i).dc>=0))*bac(i).v(1))*bac(i).dt,-1);
               end
               if abs(roundn(bac(i).cor(2)+((bac(i).u(2))+(w*(bac(i).dc>=0))*bac(i).v(2))*bac(i).dt,-1))<=30
                  bac(i).cor(2)=roundn(bac(i).cor(2)+((bac(i).u(2))+(w*(bac(i).dc>=0))*bac(i).v(2))*bac(i).dt,-1);
               end
               %A=[i bac(i).cor];
               %disp(A);
             end
          end
            bac(i).theta=normrnd(bac(i).theta, (pi*(bac(i).dc<=0)));
            bac(i).v=[cos(bac(i).theta) sin(bac(i).theta)];
            bac(i).dc=Terrain.ActualCon(round(bac(i).cor(1)/Terrain.reso)+301, round(bac(i).cor(2)/Terrain.reso)+301)-bac(i).c;
            bac(i).c=Terrain.ActualCon(round(bac(i).cor(1)/Terrain.reso)+301, round(bac(i).cor(2)/Terrain.reso)+301);
            bac(i).dis=dis(bac(i).cor(1),bac(i).cor(2));
            bac(i).st=bac(i).st+1;
            ST(i)=bac(i).st;
            bac(i).dt=exp(alpha*(bac(i).dis^2-kk^2));
            end
        end
    end
    bacteria=round(sum(ST)/numofbac);
end