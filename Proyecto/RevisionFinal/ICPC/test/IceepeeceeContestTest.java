package test;
import ICPC.*;
import java.awt.*;
import java.lang.Math;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;
import shapes.*;

/**
 * The test class IceepeeceeContestTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class IceepeeceeContestTest
{
    @Test 
    public void ShouldSolve1(){
        Number[][][] islands =  {{{20,30},{50,50},{10,50}},{{40,20},{60,10},{75,20},{60,30}},{{45,60},{55,55},{60,60},{55,65}}};
        Number[][][] flights = {{{0,30,20},{78,70,5}},{{55,0,20},{70,60,10}}};
        IceepeeceeContest contest = new IceepeeceeContest();
        try
        {
            Number solution = contest.solve(islands, flights);
            assertEquals(solution, 47.26375941509425);
        }
        catch (ICPC.IceepeeceeException ie)
        {
            ie.printStackTrace();
        }
        
    }
    
    @Test 
    public void ShouldSolve2(){
        Number[][][] islands =  {{{0,0},{10,0},{10,10},{0,10}}};
        Number[][][] flights = {{{5,5,10},{15,5,10}}};
        IceepeeceeContest contest = new IceepeeceeContest();
        try
        {
            Number solution = contest.solve(islands, flights);
            assertEquals(solution, -1);
        }
        catch (ICPC.IceepeeceeException ie)
        {
            ie.printStackTrace();
        }
    }
    
    @Test 
    public void ShouldSolveExact1(){
        double[][][] islands =  {{{20,30},{50,50},{10,50}},{{40,20},{60,10},{75,20},{60,30}},{{45,60},{55,55},{60,60},{55,65}}};
        double[][][] flights = {{{0,30,20},{78,70,5}},{{55,0,20},{70,60,10}}};
        IceepeeceeContest contest = new IceepeeceeContest();
        Number solution = contest.solveExact(islands, flights);
        assertEquals(solution, 48.031693036168775);
        }
        
    @Test
    public void ShouldSolveExact2(){
        double[][][] islands =  {{{0,0},{10,0},{10,10},{0,10}}};
        double[][][] flights = {{{5,5,10},{15,5,10}}};
        IceepeeceeContest contest = new IceepeeceeContest();
        Number solution = contest.solveExact(islands, flights);
        assertEquals(solution, -1.0);
    }
}

