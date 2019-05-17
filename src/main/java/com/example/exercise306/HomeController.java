package com.example.exercise306;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/process")
    public String processAlbum(@ModelAttribute Album album, Model model){
        Set<Song> songs = new HashSet<>();
        for(int i = 0; i<album.getNum_songs(); i++){
            songs.add(new Song());
        }
        album.setSongs(songs);
        albumRepository.save(album);
        model.addAttribute("album", album);
        return "songform";
    }

    @PostMapping("/processsong")
    public String processAlbum(@Valid Album album, BindingResult result, Model model){
        for(Song song: album.songs){
            songRepository.save(song);
        }
        if(result.hasErrors()){
            return "songform";
        }
        return "redirect:/";
    }

    @RequestMapping("/songs/{id}")
    public String showSongs(@PathVariable("id") long id, Model model){
        model.addAttribute("album",albumRepository.findById(id).get());
        model.addAttribute("songs", songRepository.findAll());
        return "show";
    }

    @RequestMapping("/delete/{id}")
    public String deleteAlbum(@PathVariable("id") long id){
        albumRepository.deleteById(id);
        return "redirect:/";
    }
}
