if(redis.call('hexists',KEYS[1], ARGV[1])==0) then
    return nil;
end;
local counter = redis.call('hincrby', KEYS[1], ARGV[1], -1);
if(counter>0) then
    redis.call('pexpire',KEYS[1],ARGV[2]);
    return "unlock-1";
else
    redis.call('del',KEYS[1]);
    return 1;
end;