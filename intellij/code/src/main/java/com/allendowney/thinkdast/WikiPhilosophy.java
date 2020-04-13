package com.allendowney.thinkdast;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

public class WikiPhilosophy {

    final static List<String> visited = new ArrayList<String>();
    final static WikiFetcher wf = new WikiFetcher();

    /**
     * Tests a conjecture about Wikipedia and Philosophy.
     *
     * https://en.wikipedia.org/wiki/Wikipedia:Getting_to_Philosophy
     *
     * 1. Clicking on the first non-parenthesized, non-italicized link
     * 2. Ignoring external links, links to the current page, or red links
     * 3. Stopping when reaching "Philosophy", a page with no links or a page
     *    that does not exist, or when a loop occurs
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        String destination = "https://en.wikipedia.org/wiki/Philosophy";
        String source = "https://en.wikipedia.org/wiki/Java_(programming_language)";

        testConjecture(destination, source, 10);
    }

    /**
     * Starts from given URL and follows first link until it finds the destination or exceeds the limit.
     *
     * @param destination
     * @param source
     * @throws IOException
     */
    public static void testConjecture(String destination, String source, int limit) throws IOException {
        // TODO: FILL THIS IN!

        String nextUrl = source;

        for(int i = 0 ; i < limit ; i ++) {
            System.out.println("순회 순서 : " + i);
            if(visited.contains(nextUrl)) {
                System.out.println("이미 방문한 페이지입니다. : " + nextUrl );
                return;
            }
            System.out.println("=======이번에 방문하는 URL : " + nextUrl);
            visited.add(nextUrl);
            Elements paras = wf.fetchWikipedia(nextUrl);
            if(paras==null) {
                System.out.println("파싱 가능한 대상이 없습니다. ");
                return;
            }

            WikiParser wp = new WikiParser(paras);
            Element firstLink = wp.findFirstLink();
            if(firstLink==null) {
                System.out.println("접근가능한 링크가 없습니다. ");
                return;
            }

            nextUrl = firstLink.attr("abs:href");

            if(nextUrl == null) {
                System.out.println("맞는 URL이 없다...");
                return;
            }else if(nextUrl.equals( destination )) {
                System.out.println("여기가 거기다!");
                break;
            }
        }


    }
}
