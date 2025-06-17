package com.apuestas_api.apuestas_back.payload.response;

import com.apuestas_api.apuestas_back.models.EReaction;

public class ReactionCountResponse {
    private EReaction reactionType;
    private Long count;

    public ReactionCountResponse(EReaction reactionType, Long count) {
        this.reactionType = reactionType;
        this.count = count;
    }

    public EReaction getReactionType() {
        return reactionType;
    }

    public Long getCount() {
        return count;
    }
}