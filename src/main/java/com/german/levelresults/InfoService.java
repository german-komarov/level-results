package com.german.levelresults;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class InfoService {

    public static final ConcurrentHashMap<Integer, CopyOnWriteArraySet<Level>> levelsByUser = new ConcurrentHashMap<>();

    public List<InfoDto> readTop20Results(int usedId) throws NotFoundException {
        if(!levelsByUser.containsKey(usedId)) {
            throw new NotFoundException(String.format("There is no user(id=%d)", usedId));
        }
        List<Level> sortedLevels = levelsByUser.get(usedId).stream().sorted().collect(Collectors.toList());
        Collections.reverse(sortedLevels);
        sortedLevels = sortedLevels.subList(0, 20);
        return sortedLevels.stream().map(l-> new InfoDto(usedId, l.getId(), l.getResult().get())).collect(Collectors.toList());
    }


    public List<InfoDto> readByTop20Users(int levelId) throws NotFoundException {
        Level level = new Level(levelId);
        // We can make just a list of available levels and check for it, but what if user have non-completed level
        if(levelsByUser.entrySet().stream().noneMatch(e->e.getValue().contains(level))) {
            throw new NotFoundException(String.format("There is no level(id=%d)", levelId));
        }
        List<Map.Entry<Integer, CopyOnWriteArraySet<Level>>> allEntries = new ArrayList<>(levelsByUser.entrySet());
        Map<Integer, Level> levelByUser = new HashMap<>();
        for(Map.Entry<Integer, CopyOnWriteArraySet<Level>> e : allEntries) {
            if(e.getValue().contains(level)) {
                levelByUser.put(e.getKey(), e.getValue().stream().filter(l->l.equals(level)).findFirst().get());
            }
        }
        List<Map.Entry<Integer, Level>> levelEntries = new ArrayList<>(levelByUser.entrySet());
        levelEntries.sort(Map.Entry.comparingByValue());
        Collections.reverse(levelEntries);
        levelEntries = levelEntries.subList(0, 20);
        return levelEntries.stream()
                .map(e-> new InfoDto(e.getKey(), levelId, e.getValue().getResult().get()))
                .collect(Collectors.toList());
    }


    public Map<String, Object> setInfo(InfoDto infoDto) throws NotFoundException {
        Integer userId = infoDto.getUser_id();
        Integer levelId = infoDto.getLevel_id();
        Integer updatedResult = infoDto.getResult();
        if(!levelsByUser.containsKey(userId)) {
            throw new NotFoundException(String.format("There is no user(id = %d)", userId));
        }
        Optional<Level> optionalLevel = levelsByUser.get(userId).stream().filter(l->l.equals(new Level(levelId))).findFirst();
        if(optionalLevel.isEmpty()) {
            throw new NotFoundException(String.format("There is no level(id=%d) for user(id=%d)", levelId, userId));
        }
        AtomicInteger currentResult = optionalLevel.get().getResult();
        currentResult.set(updatedResult);

        Map<String, Object> json = new HashMap<>();
        json.put("user_id", userId);
        json.put("level_id", levelId);
        json.put("result", updatedResult);
        return json;
    }
}
