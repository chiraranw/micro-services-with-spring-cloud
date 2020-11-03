package com.chiraranw.mslibrary.control;

import com.chiraranw.mslibrary.model.Member;
import com.chiraranw.mslibrary.model.Reading;
import com.chiraranw.mslibrary.services.LibraryBookService;
import com.chiraranw.mslibrary.services.LibraryMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/lib/v1")
public class LibraryController {

    private final LibraryMemberService libraryMemberService;
    private final LibraryBookService libraryBookService;

    @Autowired
    public LibraryController(LibraryMemberService libraryMemberService, LibraryBookService libraryBookService) {
        this.libraryMemberService = libraryMemberService;
        this.libraryBookService = libraryBookService;
    }


    @RequestMapping(value = "/mb/history/{mbId}", method = RequestMethod.GET)
    Reading getBksReadByMb(@PathVariable("mbId") Integer mbId) {
        //1. Get member details
        //2. Get Books that the member read
        Reading reading = new Reading();

        Optional<Member> optionalMember = Optional.ofNullable(this.libraryMemberService.getMb(mbId));
        optionalMember.ifPresent((res) -> {
            reading.setMember(optionalMember.get());
            reading.setBks(this.libraryBookService.getBksReadByMb(mbId));
        });

        return reading;

    }


}
