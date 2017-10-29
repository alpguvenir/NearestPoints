# NearestPoints
The purpose of the project is to read a text file where each line contains the coordinates of a multidimensional point. Furthermore, looks for the closest pair of points in the file. If the program finds the closest pair of points, it outputs the line numbers and the coordinates of the two closest points in to a file.

The NearestCoordinates project is written in Java language, following the coding conventions stated in website of Oracle.

# Asymptotical Running Time
The algorithm works in the following way:

```java
// Outer loop iterating on rows
for rowIndex = 0 to coordinateMatrix.length - 1                 // (N-1) times
  // Assign row1
  row1 <- coordinateMatrix[rowIndex]

  // Inner loop for iterating on consecutive rows
  for innerRowIndex = rowIndex + 1 to coordinateMatrix.length   // (((N-1)+1)/2) times
    // distance reinitialized to 0
    squaredDistance <- 0

    // Assign row2
    row2 <- coordinateMatrix[innerRowIndex]

    for columnIndex = 0 to row1.length                           // (M) times
      squaredDistance += (row1[columnIndex] - row2[columnIndex])^2
      
    // Since we do not need the absolute distance values, we no dot need to take the square root**
    // sqrtDistance = distance^(1/2)

    // Euclidean distance comparison
    if  squaredDistance < minimumDistance
      minRowIndex1 <- rowIndex + 1
      minRowIndex2 <- innerRowIndex + 1

      // Assign the new minimum distance
      minimumDistance <- squaredDistance
```

Here, 'N' stans for the number of points in our file of coordinates and 'M' stands for the number of dimensions.
The outer loop executes (N) times, since in the worst case we are going to iterate to the coordinate 1 before the last, which is in the order of O(N).
The inner loop executes ((N-1)+1)/2) times, since 'row2' is initialized from the next coordinate of 'row1', which is in the order of O(N).

As it was stated before, the points are in a multidimensional coordinate system. Therefore, we take square root of the sum of squares of the differences of corresponding coordinate values. Since there are 'M' number of dimension, this operation is performed in the order of O(M).

Thus, the algorithm works in the order of O(N<sup>2</sup>M).

# Limitations
The program takes the path of a '.tsv' file as an input from the user. Therefore at each execution, give path for only **one** file.
The program finds the nearest pair of coordinates **if and only if**:
 - The text file contains one point per line
 - The coordinate values are separated by a tabulator character
 - The file consists of only integers or floating point numbers
 - Floating point number use period as the decimal separator
 - The file contains no missing feature value(s)

The program assumes that values given for each dimension are at the same scale. Therefore, no feature normalization is done.

The program asks for the name of the output '.txt' file, without giving the '.txt' extension, and the path for the file to be saved into. The program assumes the user enters a valid path.

The user should be aware that if such a '.txt' file exists with the given name at the given path, the program will override the file.

# Usage
For using the NearestCoordinates, simply clone the project using 'git clone' command. Compile the project.

A sample run is given below...
Lines that start with '+' symbol indicate the user entry, just for clarification purposes, **do not** enter your input starting with a '+' sign':

```diff
Please provide the path of the tab seperated input file:
+/Users/AlpGuvenir/Desktop/n2c/sample_in_out/sample_input_2_8.tsv
The file provided is valid
Points with minimum distance are at:
Row:3 and Row:6
Time elasped: 138 nanoseconds
Please provide a name for the output file,
Do not put any extensions, it is automatically saved as a '.txt' file:
+sample_output_2_8
Please provide the path of the output '.txt' file,
the location you would like the output file to be saved:
+/Users/AlpGuvenir/Desktop/sample_output

Process finished with exit code 0
```

# Test Cases
In order to test the validity of the program, various test cases are written. These test cases are to see the response of the program assuming that the path of the file is given correctly. The test files are located under **SampleTestFiles** folder.
The files test:
| Test file name        |       File explanation|
|-----------------------|-----------------------|
| - "test1-(emptyFile).tsv"              | The file provided is empty|
| - "test2-(oneInstance).tsv"            | The file consists of one point, no other point to compare distance with|
| - "test3-(twoSameInstances).tsv"       | The file consists of two points that have same coordinate values|
| - "test4-(twoDifferentInstances).tsv"  | The file consists of two points that have different coordinate values|
| - "test5-(manySameInstances).tsv"      | The file consists of 32 points that have same coordinate values|
| - "test6-(includingCharacter).tsv"     | The file consists of 8 points all having integer values expect second row including a series of characters hidden|
| - "test7-(includingComma).tsv"         | The file consists of comma as a decimal place separator instead of period|

# Timing
For each of the sample inputs provided, a timing experiment is made using the System.nanoTime() function.
The specification of the computer that the testing was performed:
 - Processor 2.4 GHz Intel Core i5

The program is executed for each of the sample files 3 times:

|                       |    Trial 1             |   Trial 2             | Trial 3               |
|-----------------------|-----------------------:|----------------------:|----------------------:|
|sample_input_2_8       |    138     nanoseconds |   176    nanoseconds  |   200     nanoseconds |
|sample_input_3_1000    |    38650   nanoseconds |   26385  nanoseconds  |   63300   nanoseconds |
|sample_input_4_4       |    148     nanoseconds |   147    nanoseconds  |   184     nanoseconds |
|sample_input_10_100    |    5218    nanoseconds |   7716   nanoseconds  |   6414    nanoseconds |
|sample_input_100_100   |    26815   nanoseconds |   17581  nanoseconds  |   22630   nanoseconds |

# Experiment
An experiment is conducted on changing the number of points (rows) and number of dimensions (columns) independently. The input used for the experiment can be found under **ExperimentFiles**.

