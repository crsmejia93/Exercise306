package com.example.exercise306;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
public class HomeController {
    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    SongRepository songRepository;

    @GetMapping("/")
    public String showAlbums(Model model){
        model.addAttribute("albums", albumRepository.findAll());
        return "list";
    }

    @GetMapping("/addalbum")
    public String addAlbum(Model model){
        model.addAttribute("songs", songRepository.findAll());
        model.addAttribute("album", new Album());
        return "albumform";
    }

//    @GetMapping("/addsong")
//    public String addSong(Model model){
//        model.addAttribute("albums", albumRepository.findAll());
//        model.addAttribute("song", new Album());
//        return "songform";
//    }

    @PostMapping("/process")
    public String processAlbum(@Valid Album album, BindingResult result, Model model){
        model.addAttribute("album", album);
        if(result.hasErrors()){
            return "albumform";
        }
        Set<Song> songs = new HashSet<>();
        for(int i = 0; i<album.getNum_songs(); i++){
            songs.add(new Song());
        }
        album.setSongs(songs);
        albumRepository.save(album);
        return "/songform";
    }

    @PostMapping("/processsong")
    public String processAlbum(@Valid Song song, BindingResult result, Model model){
        model.addAttribute("song", song);
        if(result.hasErrors()){
            return "songform";
        }
        songRepository.save(song);
        return "redirect:/";
    }
}
