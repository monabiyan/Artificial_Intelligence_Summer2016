package entrants.pacman.username;

import java.util.Hashtable;
import java.util.PriorityQueue;

/**
 * Created by mohsennabian on 7/18/16.
 */
public class test {
    public static void main(String[] args) {
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
        pq.offer(12);
        pq.offer(13);
        pq.offer(10);
        int a=pq.remove();
        a=a+10;
        System.out.println(a);
        System.out.println("this poped out from pq   =  "+pq.remove()+100);
        System.out.println("this poped out from pq   =  "+pq.remove());


        Hashtable ff = new Hashtable();
        ff.put("ali",1);

        System.out.println((int)ff.get("ali")+2);
    }
}
