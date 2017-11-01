x= xlsread ('C:\Users\xcwan\Desktop\COT 5405\update\export concentration.xlsx','A1:A2330');
y= xlsread ('C:\Users\xcwan\Desktop\COT 5405\update\export concentration.xlsx','B1:B2330');
c= xlsread ('C:\Users\xcwan\Desktop\COT 5405\update\export concentration.xlsx','C1:C2330');
plot3 (x,y,c,'.');
grid on;
