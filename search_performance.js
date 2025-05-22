import http from 'k6/http';
import { check, sleep } from 'k6';

// k6 run search_performance.js 명령어로 성능테스트
export const options = {
    scenarios: {
        in_memory_cache_v1: {
            executor: 'constant-vus',
            exec: 'inMemory',
            vus: 10,
            duration: '30s',
        },
        redis_cache_v2: {
            executor: 'constant-vus',
            exec: 'redis',
            vus: 10,
            duration: '30s',
            startTime: '35s',
        },
    },
};

const params = {
    headers: {
        'Content-Type': 'application/json',
    },
};

const keyword = '아이유'; // 성능 비교 시 사용할 키워드

export function inMemory() {
    const res = http.get(`http://localhost:8080/api/v1/search?keyword=${keyword}`, params);
    check(res, {
        'v1 status is 200': (r) => r.status === 200,
    });
    sleep(1);
}

export function redis() {
    const res = http.get(`http://localhost:8080/api/v2/search?keyword=${keyword}`, params);
    check(res, {
        'v2 status is 200': (r) => r.status === 200,
    });
    sleep(1);
}
