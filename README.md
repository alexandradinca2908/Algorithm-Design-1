Tema 1 - Proiectarea algorimilor

Task 1 - Servere
    For the first task, I used a binary-search based algorithm. After finding the leftmost and rightmost bounds,
    the program searches through the currents and constantly updates the middle until a maximum value is found.
    What's different about this method are 2 elements:
        1. The search is integer-based, even though the final result can also be double, because after too many divisions
        the final result would end up distorted and with too many decimals. Moreover, an integer search is quicker and 
        very precise, and after finding the maximum integer value, we can just compare it to its neighbours (-0.5/+0.5);
        these are the only other possibilities for a max value, due to the properties of the function (P - |C - delay|).
        2. The criteria for choosing the new left / right / mid limits is based on the elements in mid's proximity. Since
        the maximum and minimum values of the computing powers can be represented like a sinusoidal function (kind of),
        we need to see where mid stands compared to his closest neighbours. If mid has the highest power value, it means
        we found the maximum integer current power.
   The temporal complexity of this algorithm is O(n * log(n)) [log(n) from binary search and n from parsing the power
   array at each step and calculating the total computing power].

Task 2 - Colorare
    The second task is simply based on an array iteration, where each element is processed depending on its type. I begin
    by seeing what the very first square is (horizontal = H or vertical = V). This is important because each has a unique
    property (in the beginning, H has 6 colour combinations, while V has only 3). From there, the rest of the elements
    just multiply the result by either 3, 2 or no new colour combinations (H + H adds 3, V + V adds 2 and H + V adds none).
    The only tricky aspect was to reduce multiplications, which was done with a more performant pow function that reduces
    the number of multiplications.
    The temporal complexity of this algorithm is ϴ(n * log(repeat)), where n is the number of inputs and repeat is the index
    of each input.

Task 3 - Compresie
    When it comes to the third task, my algorithm parses through the two arrays simultaneously until a partial equal sum
    is found. This works by adding the current element to its respective sum (array1 -> sum1, array2 -> sum2). If
    sum1 > sum2, it means we need to iterate more through the second array while the first waits, and vice versa. For the
    last partial sum, we iterate individually and if it doesn't match, this means our arrays weren't compatible from the
    start.
    The temporal complexity of this algorithm is O(min(n, m)), where n and m are the lengths of the arrays given.

Task 4 - Criptare
    For task 4, the algorithm required dynamic programming (the knapsack problem). Firstly, every letter occurrence
    is counted in a letter vector. Then, for each letter that appears at least once, we execute the following steps:
        each word is given a weight (length) and price (how many times the letter appears in the word) -> all words
        are sorted by (price / weight) ratio -> start adding the words with the best ratio in the password until we reach
        the limit of (price > weight / 2) => the longest password for that certain letter (repeat until max length is found)
    The temporal complexity of this algorithm is O(26 * n) which translates to O(n), where n is the number of words.

Task 5 - Oferta
    Just as task 4, this task also required dynamic programming (I used a slightly modified version of Matrix Chain
    Multiplication). This program only uses the upper half of a dp matrix. On the main diagonal, there's the full price
    of the objects; on the second diagonal, I stored the minimum price for objects i and j; on the third diagonal, you can
    find the minimum price of the objects between i and j. With these diagonals set, the algorithm continues from the 4th
    diagonal onward, calculating the minimum price between the objects from i to j, but this time it just uses the values
    that were previously calculated (unlike the other diagonals which used the offer properties). Finally, in the
    upper-right corner, there is the final result: best price.
    The temporal complexity of this algorithm is ϴ(n^2 / 2), where n is the number of products.
