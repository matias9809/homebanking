const { createApp } = Vue
createApp( {
    data(){
        return {
            post:{
                firstname:"",
                lastname:"",
                email:"",
            },
            client:[],
            account:[],
            cards:[],
            cards_credit:[],
            cards_debit:[],
            typeCard:"",
            colorCard:"",
            date:undefined,
            bool:false
        }
    },
    created(){
        this.load_data();  
        this.date=new Date().toISOString().slice(0,10)
        console.log(this.date)    
    },
    methods: {
        load_data(){
            axios.get(`/api/clients/current`)
            .then(response=>{
                this.client=response.data;
                if(this.client.account){
                    this.account=this.client.account
                }
                this.cards=response.data.cards
                let fecha=new Date(this.cards[1].fromDate).toISOString().slice(0,10)
                console.log(this.cards[1].fromDate);
                console.log(fecha);
                this.cards_debit=this.cards.filter(cards=>cards.type=='DEBIT')
                this.cards_credit=this.cards.filter(cards=>cards.type=='CREDIT')
            })
            .catch(err=>console.log(err))
        },
        logout(){
            axios.post('/api/logout')
            .then(response => location.href = "./index.html")
            .catch(err=>console.log(err))
        },
        createAccount(){
                axios.post('/api/clients/current/accounts',{
                headers:{'content-type':'application/x-www-form-urlencoded'}})
                .then(response =>{
                    console.log("your account was created successful"),
                    this.load_data()
                    })
                .catch(err=>alert(err))
        },
        createCard(){
            axios.post('/api/clients/current/cards',`typeCard=${this.typeCard}&colorCard=${this.colorCard}`,{
                headers:{
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            }).then(()=>{
                    window.location.href = '/web/cards.html';
                })
            .catch(err=>alert(err))
        },
    },

} ).mount("#app")