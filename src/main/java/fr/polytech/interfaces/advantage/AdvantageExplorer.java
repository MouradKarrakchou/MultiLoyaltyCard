package fr.polytech.interfaces.advantage;

import fr.polytech.pojo.Advantage;

import java.util.List;

public interface AdvantageExplorer {
    //public List<Advantage> getAdvantage();
    public List<Advantage> getAdvantage(int userId);
}
