n1_array = rand(12, 1);
n1_up_abs = abs(sum(n1_array) - sum(sort(n1_array, 'ascend')));
n1_up_rel = n1_up_abs/sum(n1_array);
n1_down_abs = abs(sum(n1_array) - sum(sort(n1_array, 'descend')));
n1_down_rel = n1_down_abs/sum(n1_array);

n2_array = rand(232, 1);
n2_up_abs = abs(sum(n2_array) - sum(sort(n2_array, 'ascend')));
n2_up_rel = n2_up_abs/sum(n2_array);
n2_down_abs = abs(sum(n2_array) - sum(sort(n2_array, 'descend')));
n2_down_rel = n2_down_abs/sum(n2_array);

n3_array = rand(1701, 1);
n3_up_abs = abs(sum(n3_array) - sum(sort(n3_array, 'ascend')));
n3_up_rel = n3_up_abs/sum(n3_array);
n3_down_abs = abs(sum(n3_array) - sum(sort(n3_array, 'descend')));
n3_down_rel = n3_down_abs/sum(n3_array);

x = [12, 232, 1701];
up_abs = [n1_up_abs, n2_up_abs, n3_up_abs];
down_abs = [n1_down_abs, n2_down_abs, n3_down_abs];
up_rel = [n1_up_rel, n2_up_rel, n3_up_rel];
down_rel = [n1_down_rel, n2_down_rel, n3_down_rel];

figure
plot(x, up_abs, 'r', x, down_abs, 'g');
legend('За зростанням','За спаданням');
figure
plot(x, up_rel, 'r', x, down_rel, 'g');
legend('За зростанням','За спаданням');