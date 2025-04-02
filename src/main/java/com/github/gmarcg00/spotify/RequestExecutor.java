package com.github.gmarcg00.spotify;

public abstract class RequestExecutor {
    protected RequestClient client = RequestClient.getInstance();
    protected static final String token = "BQDoNJh9AeHKUyeKwEKyJW73vcIgAcv3py79FRpippEikkmE3TRNRZwliSmA1IAbTBB4a56yDTr-vr6lPR1RZh2yAn4fCH89LFqw68NWdverSJV70P_d9rfG0zj2b_OtAi6GYgZ9NLM";

    abstract <T> T get();
    abstract <T> T post();
    abstract <T> T delete();
}
