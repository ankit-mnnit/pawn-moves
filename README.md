# pawn-moves

## Instructions for building and running the code
###
In app/src
1. mkdir out/dev/app
2. javac -d out/dev/app app/src/pawntour/\*.java app/src/\*.java

In out/dev/app
    - java Solution 10 true
    
    Usage: java Solution <length> <printBoard>
    <length> is the length of square (integer value)
    <printBoard> takes the value of 'true' or 'false' (without quotes) 
    and prints the board for a tour
    
    Output looks like this (for length = 6)
    --------------------------------------------
    34 11 18 35 10 19 
    16 26 08 13 27 07 
    23 03 29 24 04 30 
    33 12 17 36 09 20 
    15 25 05 14 28 06 
    22 02 32 21 01 31 
    
    27 tours found starting with coordinates [(0, 1), (0, 2), (0, 3), (0, 4), (1, 0), (1, 1), (1, 2), (1, 3), (1, 5), (2, 1), (2, 3), (2, 5), (3, 0), (3, 1), (3, 2), (3, 4), (3, 5), (4, 0), (4, 1), (4, 2), (4, 3), (4, 4), (4, 5), (5, 0), (5, 2), (5, 3), (5, 4)]
    01 closed tours found starting with coordinates [(3, 0)]

## Solution
### Approach #1:
In this approach, I tried backtracking for all the legal moves for pawn given the constraints.
I would visit a coordinate and then visit all the adjacent ones and their adjacent ones, till the point where 
one path may end up in visiting all cells. This was working fine for square lengths till 6. From 7 onwards it started
taking huge time. The complexity was - O(8^(n^2)) - worst case, where n is the is number of squares 
on the edge (length of square).The algorithm worked fine for n = 6, but time taken to compute hereafter, 
increased exponentially.

### Approach #2:
There was an optimization which I did on previous solution. 
I applied [Warnsdorff's rule](https://en.wikipedia.org/wiki/Knight%27s_tour#Warnsdorff.27s_rule).
It states that we build heuristics for each cell and visit cell next which has least out degree (moves from this cell).
This sped in execution as we are visiting nodes which have lower outdegree first than nodes with higher outdegree.
I also tried to generate closed tour as fun part. In the end I print all the possible tours and all 
possible closed tours count along with their origin point.

    