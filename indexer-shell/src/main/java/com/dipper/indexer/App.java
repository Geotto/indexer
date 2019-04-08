package com.dipper.indexer;

import com.dipper.indexer.cache.MyIndexerManager;
import com.dipper.indexer.model.District;
import com.dipper.indexer.model.IndexEntry;
import java.util.Scanner;
import java.util.Set;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final Logger logger = LogManager.getLogger(App.class);
    
    public static void main( String[] args )
    {
        try{        
            MyIndexerManager.instance().load();
            Scanner scanner = new Scanner(System.in);
            String line;

            System.out.print("> ");
            while(!"exit".equals((line = scanner.nextLine()))){
                long startTime = System.currentTimeMillis();
                Set<IndexEntry> result = MyIndexerManager.instance().find(line, MyIndexerManager.TYPE_DISTRICT, 20);
                long endTime = System.currentTimeMillis();
                
                for(IndexEntry entry : result){
                    Comparable data = entry.getData();
                    if(data instanceof District){
                        District store = (District)data;
                        System.out.println(String.format("%s-%s-%s-%s", store.code, store.province, store.city, store.district));
                    }
                }

                System.out.println(String.format("耗时：%d", endTime - startTime));
                System.out.print("> ");
            }
        }catch(Exception ex){
            logger.error("程序运行过程中发生错误", ex);
        }
    }
}
