package bliareg.organizer.controller;

import bliareg.organizer.model.Note;
import bliareg.organizer.model.User;
import bliareg.organizer.utils.db.NoteRepository;
import bliareg.organizer.utils.db.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by bliareg on 10.12.15.
 */

@CrossOrigin
@RestController
public class NoteRESTController {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;


    @RequestMapping(value = "/note" ,method = RequestMethod.GET, headers="Accept=application/json")
    public ResponseEntity<List<Note>> getAllNotes(){

        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findBylogin(login);

        List<Note> notes = noteRepository.findByuserid(user.getId());

        if(!(notes == null || notes.size() == 0)){
            return new ResponseEntity<List<Note>>(notes, HttpStatus.OK);
        }else{
            return new ResponseEntity<List<Note>>(HttpStatus.NO_CONTENT);
        }

    }



    @RequestMapping(value = "/note/{id}", method = RequestMethod.GET,headers="Accept=application/json")
    public ResponseEntity<Note> getNote(
            @PathVariable("id") long id){
         Note note = noteRepository.findOne(id);

        if( note!= null )
            return new ResponseEntity<Note>(note,HttpStatus.OK);
        else
            return new ResponseEntity<Note>(HttpStatus.NO_CONTENT);
    }



    @RequestMapping(value = "/note",method = RequestMethod.POST)
    public ResponseEntity<Note> newNote(@RequestBody Note note){
        if(note != null) {
            noteRepository.save(note);
            Note respNote = noteRepository.findOne(note.getId());
            return new ResponseEntity(respNote,HttpStatus.OK);
        }else
            return new ResponseEntity(HttpStatus.EXPECTATION_FAILED);
    }

    @RequestMapping(value = "/note/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Note> updateNote(
            @PathVariable("id") long id, @RequestBody Note note){

        if(!noteRepository.exists(id))
            return new ResponseEntity<Note>(HttpStatus.NO_CONTENT);
        else
        if(note==null)
            return new ResponseEntity<Note>(HttpStatus.EXPECTATION_FAILED);
        else {
            noteRepository.save(note);
            return new ResponseEntity<Note>(note, HttpStatus.OK);
        }
    }


    @RequestMapping(value = "/note", method = RequestMethod.DELETE)
    public ResponseEntity<List<Note>> deleteAllNotes(){
        noteRepository.deleteAll();
        return new ResponseEntity<List<Note>>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/note/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Note> delete(@PathVariable("id") long id){

        noteRepository.delete(id);
        if(noteRepository.exists(id))
            return new ResponseEntity<Note>(HttpStatus.EXPECTATION_FAILED);
        else
            return new ResponseEntity<Note>(HttpStatus.NO_CONTENT);
    }



    public NoteRepository getNoteRepository() {
        return noteRepository;
    }

    public void setNoteRepository(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }
}
