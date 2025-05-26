import http from 'k6/http';
import { check, sleep } from 'k6';

// k6 run search_performance.js
export const options = {
    scenarios: {
        in_memory_cache_v1: {
            executor: 'constant-vus',
            exec: 'inMemory',
            vus: 5,
            duration: '30s',
        },
        redis_cache_v2: {
            executor: 'constant-vus',
            exec: 'redis',
            vus: 5,
            duration: '30s',
            startTime: '35s',
        },
    },
};

let accessToken;

// 로그인 후 토큰 추출
export function setup() {
    const payload = JSON.stringify({
        email: 'test1@example.com',
        password: 'Test1234!',
    });

    const res = http.post('http://localhost:8080/api/auth/signin', payload, {
        headers: { 'Content-Type': 'application/json' },
    });

    console.log('login status:', res.status);
    console.log('login body:', res.body);

    const json = res.json();
    accessToken = json.result.accessToken;
}

const headers = () => ({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${accessToken}`,
});

// In-Memory 캐시 경로 테스트
export function inMemory() {
    const res = http.get('http://localhost:8080/api/songs', { headers: headers() });
    console.log(`[v1] status: ${res.status}`);
    check(res, {
        'v1 status is 200': (r) => r.status === 200,
    });
    sleep(1);
}

// Redis 캐시 경로 테스트
export function redis() {
    const res = http.get('http://localhost:8080/api/songs', { headers: headers() });
    console.log(`[v2] status: ${res.status}`);
    check(res, {
        'v2 status is 200': (r) => r.status === 200,
    });
    sleep(1);
}
