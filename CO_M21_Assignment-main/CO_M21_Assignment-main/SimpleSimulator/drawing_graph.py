import matplotlib.pyplot as plt
from sys import stdin
i = 1
x = []
y = []
with open('/mnt/d/Sem_2/CO/CO_M21_Assignment-main-finalfinal/SimpleSimulator/D:\Sem_2\CO\CO_M21_Assignment-main-finalfinal\SimpleSimulator\graph.txt') as f:
    line = f.readline()
    while line:
        
        y.append(line)
        x.append(i)
        i = i + 1
        line = f.readline()
 
plt.scatter(x, y)
plt.title("Scatter plot")
plt.xlabel("Cycle Number")
plt.ylabel("Memory Address")
plt.show()
