package lut.kj.choosepaper.topic.service;

import lut.kj.choosepaper.core.Message;
import lut.kj.choosepaper.topic.domin.Replay;

/**
 * Created by kj on 2017/4/11.
 */
public interface ReplayService {
    Message addReplay(Replay replay);
    Message deleteReplay(String[] commentIds);
}
