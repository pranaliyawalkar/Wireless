import csv
import collections
import matplotlib.pyplot as plt

filename = '4.csv'
num_iter = float(40000)
algo1 = {}
algo2 = {}

with open(filename, 'rb') as csvfile:
  reader = csv.reader(csvfile)
  for row in reader:
    # Algo 1
    slots = row[0].strip()
    if algo1.has_key(slots):
      algo1[slots] = algo1[slots] + 1
    else:
      algo1[slots] = 1

    # Algo2
    slots = row[1].strip()
    if algo2.has_key(slots):
      algo2[slots] = algo2[slots] + 1
    else:
      algo2[slots] = 1
  
algo1 = collections.OrderedDict(sorted(algo1.items()))
algo2 = collections.OrderedDict(sorted(algo2.items()))

algo1.update((x, y / num_iter) for x, y in algo1.items())
algo2.update((x, y / num_iter) for x, y in algo2.items())

algo1_cdf = 0
for key in algo1:
  algo1_cdf = algo1_cdf + algo1[key]
  algo1[key] = algo1_cdf

algo2_cdf = 0
for key in algo2:
  algo2_cdf = algo2_cdf + algo2[key]
  algo2[key] = algo2_cdf

plot_algo1 = plt.step(algo1.keys(), algo1.values(), label='Algo 1')
plot_algo2 = plt.step(algo2.keys(), algo2.values(), label='Algo 2')
# plt.legend([plot_algo1, plot_algo2], ['Algo 1', 'Algo 2'])
plt.show()

# print algo1, algo2
