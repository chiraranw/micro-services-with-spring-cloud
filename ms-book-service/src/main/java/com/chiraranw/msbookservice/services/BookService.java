package com.chiraranw.msbookservice.services;

import com.chiraranw.msbookservice.model.Book;
import com.chiraranw.msbookservice.model.ReadRecord;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    List<Book> books = Arrays.asList(
            new Book(11, "Java"),
            new Book(22, "Spring"),
            new Book(33, "Angular")
    );

    List<ReadRecord> readRecords = Arrays.asList(
            new ReadRecord(1, 111, 11),
            new ReadRecord(2, 111, 22),
            new ReadRecord(3, 222, 33)
    );

    public Optional<Book> getBk(Integer bkId) {
        return this.books.
                stream()
                .filter(bk -> bk.getId() == bkId)
                .findFirst();
    }

    public List<Book> getBks() {
        return this.books;
    }


    public List<Book> getBksReadByMb(int mbId) {
        List<Book> temp = new ArrayList<>();
        readRecords.stream()
                .filter(rr -> rr.getMbId() == mbId)
                .map(rr -> getBk(rr.getBkId()))
                .collect(Collectors.toList())
                .forEach(optionalBook -> {
                    optionalBook.ifPresent(
                            book -> temp.add(optionalBook.get())
                    );
                });

        return temp;


    }


}
