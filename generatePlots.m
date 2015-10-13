a = load('2.csv');
cdfplot(a(:,1));
hold on;
h = cdfplot(a(:,2));
set(h,'color','r');
legend('First Algorithm','Second Algorithm');