#include <cstdio>

// Accepted on UVA :)

using namespace std;
typedef long long int llt;

llt a[21][21];

void max_1(llt &A,llt n,llt i,llt j)
{
    llt Max = 0;
    for (llt k=i+1; k<=n; k++)
        if (a[k][1]+a[k][j] > Max) Max = a[k][1]+a[k][j];
    A += Max;
}
void max_2(llt &A,llt n,llt i,llt j)
{
    llt Max = 0;
    for (llt k=1; k<j; k++)
        if (a[i][k]+a[n][k]) Max = a[i][k]+a[n][k];
    A += Max;
}
void max_3(llt &A,llt i,llt j)
{
    llt Max = 0;
    for (llt k=i; i<j; i++)
        if (a[i][k]+a[k+1][j] > Max) Max = a[i][k]+a[k+1][j];
    A += Max;
}

void func(llt &A,llt n,llt i,llt j)
{
    A = 0;
    if (i >= j){
        if (i < n) max_1(A,n,i,j);
        if (j>0)   max_2(A,n,i,j);
    }
    else
        max_3(A,i,j);
}

int main()
{
    llt n,an1;
    while (scanf("%lld%lld",&n,&an1)!=EOF){
        a[n][0] = 0;
        a[n][1] = an1;
        for (llt j=2; j<=n; j++)
            func(a[n][j],n,n,j);
        for (llt i=n-1; i>0; i--)
            for (llt j=0; j<=n; j++)
                func(a[i][j],n,i,j);
        printf("%lld\n",a[1][n]);
    }

    return 0;
}